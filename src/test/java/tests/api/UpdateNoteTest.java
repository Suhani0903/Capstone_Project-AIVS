package tests.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class UpdateNoteTest {

    @Test
    public void updateNote() {
        String token = AuthManager.getToken();
        BaseApi.init();

        Response createResponse =
                given()
                        .header("x-auth-token", token)
                        .contentType("application/json")
                        .body("""
                        {
                            "title": "Old Title",
                            "description": "Old Desc",
                            "category": "Home"
                        }
                        """)
                        .when().post("/notes")
                        .then().statusCode(200)
                        .extract().response();

        String noteId = createResponse.jsonPath().getString("data.id");

        Response updateResponse =
                given()
                        .header("x-auth-token", token)
                        .contentType("application/json")
                        .body("""
                        {
                            "title": "Updated Title",
                            "description": "Updated Desc",
                            "category": "Work",
                            "completed": false
                        }
                        """)
                        .when().put("/notes/" + noteId)
                        .then().extract().response();

        Assert.assertEquals(updateResponse.getStatusCode(), 200);

        Assert.assertEquals(
                updateResponse.jsonPath().getString("data.title"), "Updated Title");

        Assert.assertEquals(
                updateResponse.jsonPath().getString("data.description"), "Updated Desc");

        Assert.assertEquals(
                updateResponse.jsonPath().getString("data.category"), "Work");

        System.out.println("NOTE UPDATED SUCCESSFULLY");
    }
}