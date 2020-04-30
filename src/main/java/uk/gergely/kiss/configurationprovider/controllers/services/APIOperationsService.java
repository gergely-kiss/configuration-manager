package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.JSONObject;

public interface APIOperationsService {

    JSONObject operate(String operationId, JSONObject request);

}
