package mx.clip.assessment.user.tx.controllers;

import mx.clip.assessment.user.tx.api.model.ErrorResponse;
import mx.clip.assessment.user.tx.service.exception.ServiceResultCode;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;

import static org.assertj.core.api.Assertions.assertThat;

class UserTransactionExceptionHandlerTest {

    private UserTransactionExceptionHandler exceptionHandler;

    @BeforeEach
    void setup() {
        exceptionHandler = new UserTransactionExceptionHandler();
    }

    @Test
    void shouldHandleHttpMessageConversionException_400() {
        ResponseEntity<ErrorResponse> responseEntity =
                exceptionHandler.handleHttpMessageConversionException(new HttpMessageConversionException("Bad request: missing body."));

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).containsIgnoringCase("Bad Request");
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("400");
    }

    @Test
    void shouldHandleUserTransactionServiceException_404() {
        ResponseEntity<ErrorResponse> responseEntity =
                exceptionHandler.handleUserTransactionServiceException(new UserTransactionServiceException("Service error",
                        ServiceResultCode.NO_DATA_FOUND));

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).containsIgnoringCase("Service error");
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("404");
    }

    @Test
    void shouldHandleUserTransactionServiceException_400() {
        ResponseEntity<ErrorResponse> responseEntity =
                exceptionHandler.handleUserTransactionServiceException(new UserTransactionServiceException("Service error",
                        ServiceResultCode.BAD_REQUEST));

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).containsIgnoringCase("Service error");
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("400");
    }

    @Test
    void shouldHandleUserTransactionServiceException_500() {
        ResponseEntity<ErrorResponse> responseEntity =
                exceptionHandler.handleUserTransactionServiceException(new UserTransactionServiceException("Service error",
                        ServiceResultCode.SERVER_ERROR));

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).containsIgnoringCase("Internal Server Error");
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("500");
    }
}
