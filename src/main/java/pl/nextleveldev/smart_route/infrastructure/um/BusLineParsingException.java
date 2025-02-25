package pl.nextleveldev.smart_route.infrastructure.um;

class BusLineParsingException extends RuntimeException {
    BusLineParsingException(String message) {
        super("Error parsing bus line info: " + message);
    }
}
