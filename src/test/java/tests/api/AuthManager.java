package tests.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import config.ConfigReader;

import static io.restassured.RestAssured.given;

public class AuthManager {

    private static final Logger logger = LogManager.getLogger(AuthManager.class);
    public static String getToken() {

        return getToken(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
    }

    public static String getToken(String email, String password) {
        BaseApi.init();
        logger.info("Generating authentication token for user: {}", email);

        Response response =
                given()
                        .contentType("application/json")
                        .body(
                                "{"
                                        + "\"email\":\"" + email + "\","
                                        + "\"password\":\"" + password + "\""
                                        + "}")
                        .when()
                        .post("/users/login")
                        .then()
                        .statusCode(200).extract().response();

        logger.info("TOKEN GENERATED SUCCESSFULLY");
        return response.jsonPath().getString("data.token");
    }
}