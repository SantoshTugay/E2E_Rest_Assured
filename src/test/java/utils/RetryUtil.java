package utils;

import io.restassured.response.Response;
import java.util.function.Supplier;

public class RetryUtil {

    public static Response retry(Supplier<Response> apiCall, int maxRetry) {

        Response response = null;

        for (int i = 1; i <= maxRetry; i++) {
            response = apiCall.get();

            if (response.statusCode() < 500) {
                return response;
            }
            System.out.println("Retrying API... Attempt " + i);
        }
        return response;
    }
}
