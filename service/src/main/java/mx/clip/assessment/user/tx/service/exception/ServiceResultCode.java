package mx.clip.assessment.user.tx.service.exception;

public enum ServiceResultCode {
    BAD_REQUEST(400),
    NO_DATA_FOUND(404),
    SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    SUCCESS(200),
    NO_CONTENT(204);

    private int status;

    ServiceResultCode(int status) {
        this.status = status;
    }
}
