package tests;

import base.BaseTest;
import models.LoginRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.SchemaValidator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginTest extends BaseTest {
    
    @Test
    public void loginAndExtractToken() {
        LoginRequest loginRequest = new LoginRequest("eve.holt@reqres.in", "cityslicka");
        
        Response response = given()
                .spec(reqSpec)
                .body(loginRequest)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();
        
        String token = response.path("token");
        System.out.println("Extracted Token: " + token);
        
        // JSON Schema Validation
        SchemaValidator.validateResponseAgainstSchema(response, "schemas/loginSchema.json");
        
        // Use the token in another request
        given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/3")
                .then()
                .statusCode(200);
    }
}