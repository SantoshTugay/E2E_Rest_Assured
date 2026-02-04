package utils;

import static io.restassured.RestAssured.given;

public class Auth0TokenManager {

    private static String accessToken;

    // 🔁 Generate token (single attempt)
    private static String generateTokenOnce() {

        return
                given()
                        .contentType("application/json")
                        .body("{"
                                + "\"client_id\":\"" + ConfigReader.get("auth0.clientId") + "\","
                                + "\"client_secret\":\"" + ConfigReader.get("auth0.clientSecret") + "\","
                                + "\"audience\":\"" + ConfigReader.get("auth0.audience") + "\","
                                + "\"grant_type\":\"client_credentials\""
                                + "}")
                        .when()
                        .post("https://" + ConfigReader.get("auth0.domain") + "/oauth/token")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("access_token");
    }

    // 🔁 Retry wrapper
    private static String generateTokenWithRetry() {

        int retries =
                Integer.parseInt(ConfigReader.get("auth0.token.retry.count"));

        for (int i = 1; i <= retries; i++) {
            try {
                return generateTokenOnce();
            } catch (Exception e) {
                if (i == retries) {
                    throw new RuntimeException(
                            "Token generation failed after retries", e);
                }
            }
        }
        return null;
    }

    // 🔐 Public access method
    public static String getToken() {
        if (accessToken == null) {
            accessToken = generateTokenWithRetry();
        }
        return accessToken;
    }

    // 🔄 Force refresh (on 401)
    public static void refreshToken() {
        accessToken = generateTokenWithRetry();
    }
}
