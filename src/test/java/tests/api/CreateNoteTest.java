package tests.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AllureUtils;

import static io.restassured.RestAssured.given;

public class CreateNoteTest {

    @Test
    public void createNoteTest() {
        String token = AuthManager.getToken();
        BaseApi.init();
        String requestBody = """
                {
                    "title": "API Note",
                    "description": "Created via API",
                    "category": "Home"
                }
                """;
        AllureUtils.attachRequest(requestBody);

        Response response =
                given()
                        .header("x-auth-token", token)
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/notes")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        AllureUtils.attachResponse(response.asPrettyString());
        Assert.assertTrue(
                response.getTime() < 2000, "Response time exceeded 2 seconds");

        Assert.assertEquals(
                response.jsonPath().getString("data.title"), "API Note");

        Assert.assertEquals(
                response.jsonPath().getString("data.description"), "Created via API");

        Assert.assertEquals(
                response.jsonPath().getString("data.category"), "Home");

        Assert.assertNotNull(response.jsonPath().getString("data.id"));
        System.out.println("NOTE CREATED SUCCESSFULLY");
    }
}