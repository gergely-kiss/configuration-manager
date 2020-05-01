package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.JSONObject;
import uk.gergely.kiss.configurationprovider.security.dto.UserDetailsDTO;

public interface APIOperationsService {

    JSONObject operate(String operationId, org.json.simple.JSONObject request, UserDetailsDTO userDetailsDTO);

}
