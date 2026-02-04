package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateBookingTest extends BaseTest {

    @Test
    public void createBooking() {

        // In our API automation framework, we externalize request payloads into JSON files under resources.
        // REST Assured directly consumes these files, which improves maintainability, supports data-driven testing,
        // and avoids hardcoding payloads in test classes.

        File payload = new File("src/test/resources/payloads/createBooking.json");

        Response response =
                given()
                        .contentType("application/json")
                        .body(payload)
                        .when()
                        .post("/booking")
                        .then().statusCode(200)
                        .body("booking.firstname", equalTo("Santosh"))
                        .extract()
                        .response();

        int bookingId = response.path("bookingid");
        System.out.println("Booking ID created: " + bookingId);
        System.out.println(response.asPrettyString());
    }
}
