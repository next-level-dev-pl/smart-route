package pl.nextleveldev.smart_route.infrastructure.um;

class BusLineResponseException extends RuntimeException {
    BusLineResponseException(String message) {
        super(message);
    }
}
