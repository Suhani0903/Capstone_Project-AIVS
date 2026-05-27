package tests.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class GetNotesTest {

    @Test
    public void validateGetNotes() {
        String token = AuthManager.getToken();
        BaseApi.init();
        Response response =
                given()
                        .header("x-auth-token", token).when()
                        .get("/notes")
                        .then().statusCode(200)
                        .extract().response();

        Assert.assertTrue(response.jsonPath().getList("data").size() >= 0);
    }
}