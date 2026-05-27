package tests.api;

import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApi {

    public static RequestSpecification requestSpec;
    public static void init() {
        RestAssured.baseURI = ConfigReader.getProperty("apiUrl");
        requestSpec = RestAssured.given().contentType("application/json");
    }
}