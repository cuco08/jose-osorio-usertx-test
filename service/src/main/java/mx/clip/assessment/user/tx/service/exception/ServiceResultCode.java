package mx.clip.assessment.user.tx.service.exception;

import java.util.Arrays;

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

    public int status() {
        return this.status;
    }

    public static ServiceResultCode fromValue(String value) {
        return Arrays.stream(values())
                .filter((domainResultCode) -> domainResultCode.name().equalsIgnoreCase(value))
                .findAny().orElse(SERVER_ERROR);
    }

    public static ServiceResultCode fromIntValue(int value) {
        return Arrays.stream(values())
                .filter((domainResultCode) -> domainResultCode.status() == value)
                .findAny().orElse(SERVER_ERROR);
    }

    public static ServiceResultCode fromIntValue(Integer value) {
        return Arrays.stream(values())
                .filter((domainResultCode) -> domainResultCode.status() == value)
                .findAny().orElse(SERVER_ERROR);
    }
}
