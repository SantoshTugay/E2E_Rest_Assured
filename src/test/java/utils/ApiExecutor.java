package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiExecutor {

    public static Response getWithOAuth(String endpoint) {

        Response response =
                given()
                        .header("Authorization",
                                "Bearer " + Auth0TokenManager.getToken())
                        .when()
                        .get(endpoint);

        // 🔁 Auto refresh on expiry
        if (response.statusCode() == 401) {
            Auth0TokenManager.refreshToken();

            response =
                    given()
                            .header("Authorization",
                                    "Bearer " + Auth0TokenManager.getToken())
                            .when()
                            .get(endpoint);
        }
        return response;
    }
}
