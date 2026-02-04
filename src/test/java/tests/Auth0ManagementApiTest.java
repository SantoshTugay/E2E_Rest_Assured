package tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.Auth0TokenManager;
import utils.ConfigReader;

public class Auth0ManagementApiTest {
//    OAuth tokens are generated centrally using a TokenManager. Token generation includes retry logic to
//    handle transient auth failures. During API execution, if a 401 is encountered,the token is
//    refreshed and the request is retried once to keep the suite stable during long runs
    @BeforeClass
    public void setup() {
        baseURI = "https://" + ConfigReader.get("auth0.domain");
    }

    @Test
    public void getClientsUsingOAuth2JWT() {

        String token = Auth0TokenManager.getToken();

        // JWT sanity validation (INTERVIEW GOLD)
        assert token.split("\\.").length == 3 : "Invalid JWT";

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v2/clients")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
