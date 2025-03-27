package pl.nextleveldev.smart_route.infrastructure.um.api;

public class TimetableResponseException extends RuntimeException {
    public TimetableResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
