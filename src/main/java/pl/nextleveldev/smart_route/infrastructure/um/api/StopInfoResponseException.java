package pl.nextleveldev.smart_route.infrastructure.um.api;

public class StopInfoResponseException extends RuntimeException {
    public StopInfoResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
