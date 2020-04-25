package uk.gergely.kiss.configurationprovider.controllers.services;

import uk.gergely.kiss.configurationprovider.controllers.model.ConfigurationRequest;

import java.util.Map;

public interface AdminService {

    Map processRequest(String operationId, ConfigurationRequest operation);
}
