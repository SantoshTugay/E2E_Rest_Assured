package tests;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import utils.ApiExecutor;
import utils.ConfigReader;

public class Auth0RetryE2ETest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI =
                "https://" + ConfigReader.get("auth0.domain");
    }

    @Test
    public void getClientsWithRetryTokenLogic() {

        ApiExecutor.getWithOAuth("/api/v2/clients")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
