package utils;

import pojo.AuthRequest;
import pojo.AuthResponse;

import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String token;

    public static String generateToken() {
        if (token == null) {
            AuthRequest request = new AuthRequest();
            request.setUsername("admin");
            request.setPassword("password123");

            AuthResponse response = given()
                    .contentType("application/json")
                    .body(request)
                    .when()
                    .post("/auth")
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(AuthResponse.class);
            token = response.getToken();
        }
        return token;
    }

    public static String getToken(){
        if (token == null) {
            token = generateToken();
        }
        return token;
    }

    public static void refreshToken() {
        token = generateToken();
    }
}
