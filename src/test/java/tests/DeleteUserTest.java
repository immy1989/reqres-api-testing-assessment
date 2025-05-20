package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest extends BaseTest {
    
    @Test
    public void deleteUserAndValidateStatusCode() {
        given()
                .spec(reqSpec)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }
}