package mx.clip.assessment.user.tx.controllers;

import lombok.extern.slf4j.Slf4j;

import mx.clip.assessment.user.tx.api.model.ErrorResponse;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class UserTransactionExceptionHandler {

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageConversionException(HttpMessageConversionException e) {

        log.error("Unexpected http message conversion error", e);

        return buildResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(UserTransactionServiceException.class)
    public ResponseEntity<ErrorResponse> handleUserTransactionServiceException(UserTransactionServiceException e) {

        HttpStatus httpStatus = HttpStatus.valueOf(e.getDomainResultCode().getStatus());

        if (httpStatus.is5xxServerError()) {
            log.error("Unexpected service error", e);
        } else {
            log.warn("Server Exception, HTTP status {}, message={}", httpStatus.value(), e.getMessage());
        }

        return buildResponseEntity(httpStatus, e);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception e) {

        if (httpStatus.is4xxClientError()) {
            return buildResponseEntity(httpStatus, e.getMessage());
        }

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String errorMessage) {

        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.message(errorMessage);
        errorResponse.timeStamp(OffsetDateTime.now().toString());
        errorResponse.status(String.valueOf(status.value()));

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
