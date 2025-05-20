package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.Arrays;

import static io.restassured.RestAssured.given;  // Add this import

public class BaseTest {
    protected static RequestSpecification reqSpec;
    
    @BeforeClass
    public static void setup() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")  
                .setContentType(ContentType.JSON)       
                .addHeader("x-api-key", "reqres-free-v1")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .build();
    }
    
    protected String getAuthToken() {
        return given()  // Now this will work
                .spec(reqSpec)
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}")
                .when()
                .post("/login")
                .then()
                .extract()
                .path("token");
    }
}