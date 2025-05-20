package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.SchemaValidator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUserTest extends BaseTest {
    
    @Test
    public void getUserByIdAndValidateFields() {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue())
                .body("support.url", notNullValue())
                .body("support.text", notNullValue())
                .extract()
                .response();
        
        // JSON Schema Validation
        SchemaValidator.validateResponseAgainstSchema(response, "schemas/getUserSchema.json");
    }
}