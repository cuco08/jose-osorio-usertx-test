package mx.clip.assessment.user.tx.service.exception;

public class UserTransactionServiceException extends RuntimeException {
    private ServiceResultCode domainResultCode;
    private String errorCode;
    private String callTrace;

    public UserTransactionServiceException(String message, ServiceResultCode domainResultCode) {
        this(message, null, domainResultCode);
    }

    public UserTransactionServiceException(String message, Throwable cause, ServiceResultCode domainResultCode) {
        this(message, cause, domainResultCode, domainResultCode != null ? domainResultCode.name() : null, null);
    }

    public UserTransactionServiceException(String message, ServiceResultCode domainResultCode, String errorCode) {
        this(message, (Throwable)null, domainResultCode, errorCode, null);
    }

    public UserTransactionServiceException(String message, Throwable cause, ServiceResultCode domainResultCode, String errorCode) {
        this(message, cause, domainResultCode, errorCode, null);
    }

    public UserTransactionServiceException(String message, Throwable cause, ServiceResultCode domainResultCode, String errorCode, String callTrace) {
        super(message, cause);
        this.domainResultCode = domainResultCode;
        this.errorCode = errorCode;
        this.callTrace = callTrace;
    }

    public ServiceResultCode getDomainResultCode() {
        return this.domainResultCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getCallTrace() {
        return this.callTrace;
    }
}
