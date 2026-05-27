package tests.api;

import io.restassured.module.jsv.JsonSchemaValidator;
import utils.AllureUtils;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class NotesService {

    public static Response getAllNotes(String token) {
        String requestDetails = "GET /notes\n" + "Token: " + token;
        AllureUtils.attachRequest(requestDetails);

        Response response = given()
                .header("x-auth-token", token)
                .when()
                .get("/notes")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/notes-schema.json"))
                .extract().response();
        System.out.println(response.asPrettyString());

        AllureUtils.attachResponse(response.asPrettyString());
        return response;
    }

    public static Response deleteNote(String token, String noteId) {
        return given()
                .header("x-auth-token", token).when()
                .delete("/notes/" + noteId).then()
                .statusCode(200).extract().response();
    }
}
