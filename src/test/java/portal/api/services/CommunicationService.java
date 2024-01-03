package portal.api.services;

import portal.api.endpoints.CommunicationEndpoints;
import portal.models.communications.CommunicationApiBody;
import portal.utils.PropertiesReader;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CommunicationService extends GenericService<CommunicationService> {

    private RequestSpecification specForCommunication;

    public static CommunicationService getCommunicationService() {
        return new CommunicationService();
    }

    public CommunicationService () {
        String communicationBaseURI =
                PropertiesReader.getDefaultOrPreferredSetting(
                        "communicationApiEndpoint",
                        "communicationApiEndpoint");
        specForCommunication =
                given()
                        .baseUri(communicationBaseURI);
    }

    /**
     * Method to create a new communication
     *
     * @param cookies cookies
     * @param applicationKey applicationKey
     * @param name comm name
     * @param description comm description
     * @param uuid uuid
     * @return apiResponse
     */
    public Response createNewCommunication(Cookies cookies, String applicationKey, String name, String description, String uuid) {
        CommunicationApiBody communicationApiBody = CommunicationApiBody.builder()
                .name(name)
                .description(description)
                .id(uuid).build();

        Response response =
                given(specForCommunication).
                        cookies(cookies).
                        contentType("application/json").
                        pathParam("applicationKey", applicationKey).
                        body(communicationApiBody).
                        when().
                        post(CommunicationEndpoints.CREATE_COMMUNICATION).
                        then().log().all().
                        extract().response();
        return response;
    }
}