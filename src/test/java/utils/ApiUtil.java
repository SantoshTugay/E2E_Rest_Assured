package utils;

import io.restassured.response.Response;
import specs.RequestSpecFactory;

import static io.restassured.RestAssured.given;

public class ApiUtil {

    public static Response deleteWithAuth(String endpoint) {
        Response response = given()
                .spec(RequestSpecFactory.getRequestSpec())
                .cookie("token", TokenManager.getToken())
                .when()
                .delete(endpoint);

        if (response.statusCode() == 401) {
            TokenManager.refreshToken();

            response = given()
                    .spec(RequestSpecFactory.getRequestSpec())
                    .cookie("token", TokenManager.getToken())
                    .when()
                    .delete(endpoint);
        }
        return response;
    }
}
