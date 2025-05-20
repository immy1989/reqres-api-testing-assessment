package tests;

import base.BaseTest;
import models.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.SchemaValidator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateUserTest extends BaseTest {
    
    @Test
    public void updateUserAndValidateResponse() {
        User updatedUser = new User("morpheus@reqres.in", "Morpheus", "Zion Resident");
        
        Response response = given()
                .spec(reqSpec)
                .body(updatedUser)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("first_name", equalTo(updatedUser.getFirst_name()))
                .body("last_name", equalTo(updatedUser.getLast_name()))
                .body("updatedAt", notNullValue())
                .extract()
                .response();
        
        // JSON Schema Validation
        SchemaValidator.validateResponseAgainstSchema(response, "schemas/updateUserSchema.json");
    }
}