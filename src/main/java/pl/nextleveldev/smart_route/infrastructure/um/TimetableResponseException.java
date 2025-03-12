package pl.nextleveldev.smart_route.infrastructure.um;

public class TimetableResponseException extends RuntimeException {
    public TimetableResponseException(String message) {
        super(message);
    }
}
