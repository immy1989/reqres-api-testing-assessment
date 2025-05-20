package utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;

public class SchemaValidator {
    public static void validateResponseAgainstSchema(Response response, String schemaPath) {
        InputStream schemaStream = SchemaValidator.class.getClassLoader().getResourceAsStream(schemaPath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaStream));
    }
}