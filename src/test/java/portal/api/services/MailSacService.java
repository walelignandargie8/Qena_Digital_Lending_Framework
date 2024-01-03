package portal.api.services;

import portal.api.endpoints.MailSacEndpoint;
import portal.constants.HttpStatus;
import portal.utils.PropertiesReader;
import portal.utils.StringUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MailSacService extends GenericService<MailSacService> {

    public static final int MAX_ATTEMPTS = 5;
    public static final String MAILSAC_API_ENDPOINT = "mailsacApiEndpoint";
    public static final String COMMUNICATION_API_ENDPOINT = "gatewayApiEndpoint";

    public static MailSacService getmailSacService() {
        return new MailSacService();
    }

    /**
     * Method to verify Email is generated in Mailsac site
     *
     * @return
     */
    public String getMessageID(String emailAddress, String subject) {
        boolean emailFound = false;
        String messageId = null;
        for (int attempts = 1; attempts <= MAX_ATTEMPTS && !emailFound; attempts++) {
            baseURI = PropertiesReader.getDefaultOrPreferredSetting(MAILSAC_API_ENDPOINT, MAILSAC_API_ENDPOINT);
            Response response = given()
                    .contentType("application/json")
                    .when()
                    .header("Mailsac-Key", PropertiesReader.getDefaultOrPreferredSetting("Mailsac-Key", "Mailsac-Key"))
                    .pathParam("email", emailAddress)
                    .log().uri()
                    .get(MailSacEndpoint.GET_MAILSAC_ALL_EMAILS);
            logInformationMessage("Mailsac service using URI: " + baseURI);
            if (response.getStatusCode() == HttpStatus.OK_RESPONSE) {
                JsonPath jsonPathEvaluator = response.jsonPath();
                ArrayList<String> arrayListSubjects = jsonPathEvaluator.get("subject");
                ArrayList<String> arrayListIDs = jsonPathEvaluator.get("_id");
                for (int listIndex = 0; listIndex < arrayListSubjects.size() && !emailFound; listIndex++) {
                    if (arrayListSubjects.get(listIndex).contains(subject)) {
                        logInformationMessage("Email '%s' with subject '%s' was found", emailAddress, subject);
                        messageId = arrayListIDs.get(listIndex);
                        emailFound = true;
                    }
                }
            }

            if (!emailFound) {
                try {
                    // adding sleep in order to let the incoming mail to arrive to the inbox
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logWarningMessage("Email '%s' with subject '%s' was NOT found in '%s' attempt", emailAddress, subject, attempts);
            }
        }
        baseURI = PropertiesReader.getDefaultOrPreferredSetting(COMMUNICATION_API_ENDPOINT, COMMUNICATION_API_ENDPOINT);
        assertThat(String.format("Email '%s' with subject '%s' was NOT found", emailAddress, subject),
                messageId, is(notNullValue()));
        return messageId;
    }

    /**
     * Method to verify Email content in Mailsac site
     *
     * @return
     */
    public String getEmailBodyContent(String emailAddress, String messageId) {
        baseURI = PropertiesReader.getDefaultOrPreferredSetting(MAILSAC_API_ENDPOINT, MAILSAC_API_ENDPOINT);
        Response response = given()
                .contentType("application/json")
                .when().header("Mailsac-Key", PropertiesReader.getDefaultOrPreferredSetting("Mailsac-Key", "Mailsac-Key"))
                .pathParam("email", emailAddress)
                .pathParam("messageId", messageId)
                .get(MailSacEndpoint.GET_MAILSAC_BODY_MESSAGE);
        assertThat("Mismatching response status code: ", response.getStatusCode(), is(HttpStatus.OK_RESPONSE));
        baseURI = PropertiesReader.getDefaultOrPreferredSetting(COMMUNICATION_API_ENDPOINT, COMMUNICATION_API_ENDPOINT);
        return response.getBody().asString();
    }

    /**
     * Method to generate random email
     *
     * @return random email
     */
    public String generateRandomEmail(){
        return "comm.recipient." + StringUtils.generateAlphabeticString(5) + "@mailsac.com";
    }
}