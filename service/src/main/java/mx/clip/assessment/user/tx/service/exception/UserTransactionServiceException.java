package mx.clip.assessment.user.tx.service.exception;

import lombok.Getter;

@Getter
public class UserTransactionServiceException extends RuntimeException {
    private ServiceResultCode domainResultCode;

    public UserTransactionServiceException(String message, ServiceResultCode domainResultCode) {
        super(message);
        this.domainResultCode = domainResultCode;
    }
}
