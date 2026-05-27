package utils;
import io.qameta.allure.Allure;
public class AllureUtils {

    public static void attachResponse(String response) {

        Allure.addAttachment(
                "API Response",
                "application/json",
                response, ".json");
    }

    public static void attachRequest(String request) {

        Allure.addAttachment(
                "API Request",
                "application/json",
                request, ".json");
    }
}