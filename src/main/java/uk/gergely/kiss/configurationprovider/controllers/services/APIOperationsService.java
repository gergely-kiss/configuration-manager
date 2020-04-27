package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.simple.JSONObject;

public interface APIOperationsService {

    JSONObject operate(String operationId, JSONObject request);

}
