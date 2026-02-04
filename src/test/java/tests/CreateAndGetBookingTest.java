package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateAndGetBookingTest extends BaseTest {

    @Test
    public void createAndGetBooking(){

        File payload=new File("src/test/resources/payloads/createBooking.json");
        // POST Request
        Response response=
                given()
                        .contentType("application/json")
                        .body(payload)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        int bookingId=response.path("bookingid");
        System.out.println("Created Booking ID: " + bookingId);

        //GET
        //We implement API chaining by extracting dynamic identifiers from create APIs
        // and reusing them in subsequent GET/PUT/DELETE calls. This avoids hardcoding,
        // ensures test independence, and validates end-to-end business flows.


        given()
                .when()
                .get("/booking/"+bookingId)
                .then().log().all()
                .statusCode(200)
                .body("firstname",equalTo("Santosh"))
                .body("lastname", equalTo("Kumar"))
                .body("totalprice", equalTo(1200))
                .body("additionalneeds", equalTo("Breakfast"))
                .body("depositpaid", equalTo(true));
    }
}
