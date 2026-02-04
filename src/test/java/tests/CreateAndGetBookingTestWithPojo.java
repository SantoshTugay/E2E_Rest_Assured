package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.BookingDates;
import pojo.BookingRequest;
import pojo.BookingResponse;
import specs.RequestSpecFactory;
import specs.ResponseSpecFactory;
import utils.RetryUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class CreateAndGetBookingTestWithPojo extends BaseTest {


    @Test
    public void createAndGetBookingUsingPOJO() {
        // 1. Prepare Request Data
        BookingDates dates = new BookingDates();
        dates.setCheckin("2026-02-10");
        dates.setCheckout("2026-02-15");

        BookingRequest request = new BookingRequest();
        request.setFirstname("Santosh");
        request.setLastname("Kumar");
        request.setTotalprice(1200);
        request.setDepositpaid(true);
        request.setBookingdates(dates);
        request.setAdditionalneeds("Breakfast");

        // 2. POST - Create Booking
        // 🔹 SERIALIZATION happens here
//        Serialization is the process of converting Java objects (POJOs) into JSON
//        request bodies using Jackson before sending them to the API.
        BookingResponse response =
                given()
                        .spec(RequestSpecFactory.getRequestSpec())
                        .body(request)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(ResponseSpecFactory.getResponseSpec())
                        .extract()
                        .as(BookingResponse.class);

        int bookingId = response.getBookingid();

        // 3. GET - Verify Booking
        given()
                .spec(RequestSpecFactory.getRequestSpec())
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Santosh"))
                .body("lastname", equalTo("Kumar"));

        // 4. DELETE - Clean up (Fixed placement and scope)
        Response deleteResponse = RetryUtil.retry(
                () -> given()
                        .spec(RequestSpecFactory.getRequestSpec()) // Ensure spec is included for Auth
                        .when()
                        .delete("/booking/" + bookingId),
                2
        );

        // Validation for Delete (using anyOf for flexibility)
        deleteResponse.then().statusCode(anyOf(is(201), is(204)));
    }
}