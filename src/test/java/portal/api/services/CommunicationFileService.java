package portal.api.services;

import portal.api.endpoints.CommunicationFileEndpoints;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CommunicationFileService extends GenericService<CommunicationFileService> {
    public CommunicationFileService() {
    }

    public static CommunicationFileService getCommunicationService() {
        return new CommunicationFileService();
    }

    /**
     * Method to upload a CSV file
     *
     * @param applicationKey applicationKey
     * @param csv            CSV file
     */
    public void uploadCSVFile(String applicationKey, File csv) {
        logInformationMessage("Uploading the CSV file '%s' to the ApplicationID '%s'", csv.getName(), applicationKey);
        given().
                multiPart("file", csv).
                pathParam("applicationKey", applicationKey).
                when().
                post(CommunicationFileEndpoints.UPLOAD_CSV_FILE).
                then().
                assertThat().statusCode(201).
                log().all().
                extract().response();
    }

    /**
     * Method to get the names of the CSV files
     *
     * @param applicationKey applicationKey
     * @return the list of names of the CSV files
     */
    public List<String> getNamesOfCSVFiles(String applicationKey) {
        logInformationMessage("Getting all the CSV files from the ApplicationID : " + applicationKey);
        List<String> namesCSV =
                given().
                        pathParam("applicationKey", applicationKey).
                        when().
                        get(CommunicationFileEndpoints.GET_CSV_FILES).
                        then().
                        assertThat().statusCode(200).
                        assertThat().contentType(ContentType.JSON).
                        log().all().
                        extract().path("items.name");
        return namesCSV;
    }

    /**
     * Method to update an existing CSV file
     *
     * @param applicationKey applicationKey
     * @param csvUpdated     CSV file updated
     */
    public void updateCSVFile(String applicationKey, File csvUpdated) {
        logInformationMessage("Updating the CSV file '%s' in the ApplicationID '%s'", csvUpdated.getName(), applicationKey);
        given().
                multiPart("file", csvUpdated).
                pathParam("applicationKey", applicationKey).
                pathParam("fileName", csvUpdated.getName()).
                when().
                put(CommunicationFileEndpoints.UPDATE_CSV_FILE).
                then().
                assertThat().statusCode(200).
                log().all();
    }

    /**
     * Method to get the CSV file content
     *
     * @param applicationKey applicationKey
     * @param csvFileName    CSV file name
     * @param cookies
     * @return
     */
    public String getCSVContent(String applicationKey, String csvFileName, Cookies cookies) {

        String response = given().
                cookies(cookies).
                pathParam("applicationKey", applicationKey).
                pathParam("fileName", csvFileName).
                when().
                get(CommunicationFileEndpoints.GET_CSV_FILE_CONTENT).
                then().
                assertThat().statusCode(200).
                log().all().
                extract().body().asString();
        return response;
    }
}
