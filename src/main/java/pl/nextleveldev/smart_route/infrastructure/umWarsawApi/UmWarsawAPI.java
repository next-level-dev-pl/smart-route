package pl.nextleveldev.smart_route.infrastructure.umWarsawApi;

public interface UmWarsawAPI {

    // TODO: prepare model like 'stopDto', add validation
    String getSupportedBusLinesAtStop(String stopId, String stopNr);
}
