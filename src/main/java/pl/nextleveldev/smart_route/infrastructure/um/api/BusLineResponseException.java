package pl.nextleveldev.smart_route.infrastructure.um.api;

public class BusLineResponseException extends RuntimeException {
    public BusLineResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
