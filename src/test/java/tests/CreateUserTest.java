package tests;

import base.BaseTest;
import models.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.SchemaValidator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {
    
    @Test
    public void createNewUserAndValidateResponse() {
        User newUser = new User("morpheus@reqres.in", "Morpheus", "Leader");
        
        Response response = given()
                .spec(reqSpec)
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("first_name", equalTo(newUser.getFirst_name()))
                .body("last_name", equalTo(newUser.getLast_name()))
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .extract()
                .response();
        
        // JSON Schema Validation
        SchemaValidator.validateResponseAgainstSchema(response, "schemas/createUserSchema.json");
    }
}