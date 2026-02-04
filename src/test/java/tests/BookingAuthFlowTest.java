package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.BookingDates;
import pojo.BookingRequest;
import specs.RequestSpecFactory;
import utils.ApiUtil;
import utils.TokenManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingAuthFlowTest {

//    We handle token expiry by retrying authenticated calls once after regenerating the
//    token on a 401 response. This avoids unnecessary auth calls while keeping the test
//    suite stable for long executions.


    @Test
    public void createAndDeleteBookingWithAuth(){

        BookingDates dates = new BookingDates();
        dates.setCheckin("2026-02-10");
        dates.setCheckout("2026-02-15");

        BookingRequest request = new BookingRequest();
        request.setFirstname("Santosh");
        request.setLastname("Kumar");
        request.setTotalprice(1500);
        request.setDepositpaid(true);
        request.setBookingdates(dates);
        request.setAdditionalneeds("Breakfast");

        // CREATE BOOKING

        int bookingId =
                given()
                        .spec(RequestSpecFactory.getRequestSpec())
                        .body(request)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .body("booking.firstname", equalTo("Santosh"))
                        .extract()
                        .path("bookingid");

        System.out.println("Booking ID: " + bookingId);

        // DELETE BOOKING (AUTH REQUIRED)
        given()
                .spec(RequestSpecFactory.getRequestSpec())
                .cookie("token", TokenManager.generateToken())
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201);

        //DELETE BOOKING (AUTH REQUIRED WITH TOKEN EXPIRY and RETRY MECHANISM)
        Response response = ApiUtil.deleteWithAuth("/booking/" + bookingId);
        response.then().statusCode(201);

    }
}
