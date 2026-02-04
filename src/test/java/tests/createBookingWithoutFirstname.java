package tests;

import base.BaseTest;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import pojo.BookingDates;
import pojo.BookingRequest;
import specs.RequestSpecFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

public class createBookingWithoutFirstname extends BaseTest {

    @Test
    public void createBookingWithNoFirstname() {

        BookingDates bookingDates=new BookingDates();
        bookingDates.setCheckin("2026-01-01");
        bookingDates.setCheckout("2026-01-15");

        BookingRequest request = new BookingRequest();
       // request.setFirstname("Santosh");
        request.setLastname("Kumar");
        request.setTotalprice(1200);
        request.setDepositpaid(true);
        request.setBookingdates(bookingDates);

        given().spec(RequestSpecFactory.getRequestSpec())
                .body(request)
                .when()
                .post("/booking")
                .then()
                .statusCode(400);
    }
}
