package tests.api;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class NegativeApiTest {
    @Test
    public void getNotesWithInvalidToken() {
        BaseApi.init();
        Response response = given()
                        .header("x-auth-token", "invalid_token").when()
                        .get("/notes").then().extract().response();

        Assert.assertEquals(response.getStatusCode(), 401);
        System.out.println("INVALID TOKEN VALIDATION SUCCESSFUL");
    }

    @Test
    public void createNoteWithoutTitle() {
        String token = AuthManager.getToken();
        BaseApi.init();
        String requestBody = """
                {
                    "description": "Missing title",
                    "category": "Home"
                }
                """;
        Response response =
                given()
                        .header("x-auth-token", token)
                        .contentType("application/json")
                        .body(requestBody).when()
                        .post("/notes").then().extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 400);
        System.out.println("MISSING TITLE VALIDATION SUCCESSFUL");
    }

    @Test(description = "Verify delete note with invalid ID")
    public void deleteInvalidNoteId() {
        BaseApi.init();
        String token = AuthManager.getToken();
        Response response =
                given()
                        .header("x-auth-token", token).when()
                        .delete("/notes/invalid123").then()
                        .extract().response();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains("valid ID"), "Expected valid ID error message");

        System.out.println("INVALID NOTE ID VALIDATION SUCCESSFUL");
    }
}