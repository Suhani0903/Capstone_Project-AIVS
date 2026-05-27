package tests.api;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ApiResponseTimeTest {

    @Test
    public void verifyGetNotesResponseTime() {
        String token = AuthManager.getToken();
        BaseApi.init();
        Response response =
                given().header("x-auth-token", token)
                        .when().get("/notes")
                        .then().statusCode(200)
                        .extract().response();

        long responseTime = response.getTime();
        System.out.println("API Response Time = " + responseTime + " ms");
        Assert.assertTrue(responseTime < 2000, "API response exceeded 2 seconds");
    }
}