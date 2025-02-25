package pl.nextleveldev.smart_route.infrastructure.um;

class BusLineInfoParsingException extends RuntimeException {
    BusLineInfoParsingException(String message) {
        super("Error parsing bus line info: " + message);
    }
}
