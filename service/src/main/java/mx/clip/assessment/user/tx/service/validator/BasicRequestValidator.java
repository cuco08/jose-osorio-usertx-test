package mx.clip.assessment.user.tx.service.validator;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.service.exception.ServiceResultCode;
import mx.clip.assessment.user.tx.service.exception.UserTransactionServiceException;

import java.util.Optional;

public class BasicRequestValidator {

    private BasicRequestValidator() {}

    public static void validate(AddUserTransactionRequest request) {
        Optional.ofNullable(request)
                .map(AddUserTransactionRequest::getUserId)
                .orElseThrow(() -> new UserTransactionServiceException("Missing required param: userId",
                        ServiceResultCode.BAD_REQUEST));

        Optional.ofNullable(request)
                .map(AddUserTransactionRequest::getDate)
                .orElseThrow(() -> new UserTransactionServiceException("Missing required param: date",
                        ServiceResultCode.BAD_REQUEST));

        Optional.ofNullable(request)
                .map(AddUserTransactionRequest::getDescription)
                .orElseThrow(() -> new UserTransactionServiceException("Missing required param: description",
                        ServiceResultCode.BAD_REQUEST));

        if (request.getAmount() <= 0.0 ) {
            throw new UserTransactionServiceException("Invalid amount, it should be greater than 0.0",
                    ServiceResultCode.BAD_REQUEST);
        }
    }
}
