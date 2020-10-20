package mx.clip.assessment.user.tx.service.validator;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BasicRequestValidatorTest {

    @Test
    void shouldThrowExceptionWhenNullRequest() {
        assertThrows(UserTransactionServiceException.class, () ->
            BasicRequestValidator.validate(null)
        );
    }

    @Test
    void shouldThrowExceptionWhenEmptyRequest() {
        assertThrows(UserTransactionServiceException.class, () ->
                BasicRequestValidator.validate(new AddUserTransactionRequest())
        );
    }

    @Test
    void shouldThrowExceptionWithoutUserId() {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        request.setDate("2020-10-19");
        request.setDescription(UUID.randomUUID().toString());
        request.setAmount(1.0);
        assertThrows(UserTransactionServiceException.class, () ->
                BasicRequestValidator.validate(request)
        );
    }

    @Test
    void shouldThrowExceptionWithoutDate() {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        request.setUserId(UUID.randomUUID().toString());
        request.setDescription(UUID.randomUUID().toString());
        request.setAmount(1.0);
        assertThrows(UserTransactionServiceException.class, () ->
                BasicRequestValidator.validate(request)
        );
    }

    @Test
    void shouldThrowExceptionWithoutDescription() {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        request.setUserId(UUID.randomUUID().toString());
        request.setDate("2020-10-19");
        request.setAmount(1.0);
        assertThrows(UserTransactionServiceException.class, () ->
                BasicRequestValidator.validate(request)
        );
    }

    @Test
    void shouldThrowExceptionWithoutAmount() {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        request.setUserId(UUID.randomUUID().toString());
        request.setDate("2020-10-19");
        request.setDescription(UUID.randomUUID().toString());
        assertThrows(UserTransactionServiceException.class, () ->
                BasicRequestValidator.validate(request)
        );
    }

    @Test
    void shouldPass() {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        request.setUserId(UUID.randomUUID().toString());
        request.setDate("2020-10-19");
        request.setDescription(UUID.randomUUID().toString());
        request.setAmount(10.0);
        assertDoesNotThrow(() ->
                BasicRequestValidator.validate(request)
        );
    }
}
