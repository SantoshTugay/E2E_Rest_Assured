package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import specs.RequestSpecFactory;
import specs.ResponseSpecFactory;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateAndGetBookingTestWithSpec {

    @Test
    public void createAndGetBooking() {
        // We use RequestSpecBuilder and ResponseSpecBuilder to centralize request configuration
        // and response validations.This removes duplication, improves maintainability,
        // and allows changes like headers, authentication, or SLAs to be handled in one place.

        File payload = new File("src/test/resources/payloads/createBooking.json");

        // CREATE
        Response createResponse =
                given()
                        .spec(RequestSpecFactory.getRequestSpec())
                        .body(payload)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(ResponseSpecFactory.getResponseSpec())
                        .extract()
                        .response();

        int bookingId = createResponse.path("bookingid");

        // GET
        given()
                .spec(RequestSpecFactory.getRequestSpec())
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Santosh"))
                .body("lastname", equalTo("Kumar"));
    }
}

