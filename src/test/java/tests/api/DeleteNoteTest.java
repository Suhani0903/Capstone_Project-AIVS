package tests.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class DeleteNoteTest {

    @Test
    public void deleteNote() {
        String token = AuthManager.getToken();
        BaseApi.init();
        Response createResponse =
                given()
                        .header("x-auth-token", token)
                        .contentType("application/json")
                        .body("""
                                {
                                    "title":"Test Note",
                                    "description":"Test Desc",
                                    "category":"Home"
                                }
                                """)
                        .when()
                        .post("/notes")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String noteId = createResponse.jsonPath().getString("data.id");

        Response deleteResponse =
                given()
                        .header("x-auth-token", token)
                        .when()
                        .delete("/notes/" + noteId)
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(deleteResponse.getStatusCode(), 200);

        Response getAllNotes =
                given()
                        .header("x-auth-token", token)
                        .when()
                        .get("/notes")
                        .then()
                        .extract()
                        .response();

        String deletedNote =
                getAllNotes.jsonPath().getString("data.find { it.id == '" + noteId + "' }");

        Assert.assertNull(deletedNote, "Deleted note still exists");
        System.out.println("NOTE DELETED SUCCESSFULLY");
    }
}