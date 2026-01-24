package co.com.ecommerce.rest.helper;


import co.com.ecommerce.rest.exception.ErrorResponse;
import co.com.ecommerce.rest.handler.GlobalExceptionHandler;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public final class ErrorResponseAssert {

    private ErrorResponseAssert() {}

    public static void assertValidationError(ErrorResponse error, String... expectedFields) {
        assertThat(error).isNotNull();
        assertThat(error.error()).isEqualTo("Bad Request");
        assertThat(error.message()).isEqualTo(GlobalExceptionHandler.VALIDATION_ERROR);
        Map<String, String> fieldErrors = error.fieldErrors();
        assertThat(fieldErrors).isNotNull();
        for (String field : expectedFields) {
            assertThat(fieldErrors).containsKey(field);
        }
    }

    public static void assertNotFound(ErrorResponse error, String expectedMessage) {
        assertThat(error).isNotNull();
        assertThat(error.error()).isEqualTo("Not Found");
        assertThat(error.message()).contains(expectedMessage);
    }
}
