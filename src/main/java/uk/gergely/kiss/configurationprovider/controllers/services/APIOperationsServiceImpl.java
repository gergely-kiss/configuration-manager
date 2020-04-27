package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;
import uk.gergely.kiss.configurationprovider.security.services.PasswordManagerService;
import uk.gergely.kiss.configurationprovider.security.services.RegisterApplicationService;

@Service
public class APIOperationsServiceImpl implements APIOperationsService {
    private final RegisterApplicationService registerApplicationService;
    private final PasswordManagerService passwordManagerService;

    public APIOperationsServiceImpl(RegisterApplicationService registerApplicationService, PasswordManagerService passwordManagerService) {
        this.registerApplicationService = registerApplicationService;
        this.passwordManagerService = passwordManagerService;
    }

    @Override
    public JSONObject operate(String operationId, JSONObject request) {
        switch (operationId.toLowerCase()) {
            case ControllerConstants.REGISTER:
                return register(request);
            case ControllerConstants.UN_REGISTER:
                return unRegister(request);
            case ControllerConstants.RETURN_ALL_APP:
                return getAllApp();
            case ControllerConstants.RETURN_ALL_APP_WITH_ALL_CONFIG:
                return getAllAppAndAllConfig();
            case ControllerConstants.UPDATE_PASSWORD:
                return updatePassword(request);
            case ControllerConstants.ADD_UPDATE_CONFIG:
                return saveConfig(request);
            case ControllerConstants.REMOVE_CONFIG:
                return removeConfig(request);
            case ControllerConstants.RETURN_ALL_CONFIG:
                return getAllConfig(request);
        }
        JSONObject oprationOptions = new JSONObject();
        oprationOptions.put(ControllerConstants.ADD_UPDATE_CONFIG, "Add new or update already existing configuration");
        oprationOptions.put(ControllerConstants.REMOVE_CONFIG, "Remove already existing configuration");
        oprationOptions.put(ControllerConstants.RETURN_ALL_CONFIG, "Return all already existing configuration");
        oprationOptions.put(ControllerConstants.REGISTER, "Register new application");
        oprationOptions.put(ControllerConstants.UN_REGISTER, "Remove an already existing application. Warning: this operation will remove every already existing configuration relating to that application too!");
        oprationOptions.put(ControllerConstants.RETURN_ALL_APP, "Get all registered applications");
        oprationOptions.put(ControllerConstants.RETURN_ALL_APP_WITH_ALL_CONFIG, "Get all registered applications and related configurations");
        oprationOptions.put(ControllerConstants.UPDATE_PASSWORD, "Change the password");
        JSONObject response = new JSONObject();
        response.put("Error", "No valid operation find by operation id (" + operationId + ") please use a valid operation id");
        response.put("Operation Options", oprationOptions);
        return response;
    }

    private JSONObject getAllConfig(JSONObject request) {
        return new JSONObject();
    }

    private JSONObject removeConfig(JSONObject request) {
        return new JSONObject();
    }

    private JSONObject saveConfig(JSONObject request) {
        return new JSONObject();
    }

    private JSONObject updatePassword(JSONObject request) {
        return new JSONObject();
    }

    private JSONObject getAllAppAndAllConfig() {
        return new JSONObject();
    }

    private JSONObject getAllApp() {

        for(RegisteredApplicationEntity registeredApplicationEntity : registerApplicationService.findAll()){

            for (String plainPassword : passwordManagerService.getAllPlanPassword()){

            }
        }
        return new JSONObject();
    }

    private JSONObject unRegister(JSONObject request) {
        validateAppIdAndPasswordFields(request);
        String appId = String.valueOf(request.get(ControllerConstants.APP_ID));
        String password = String.valueOf(request.get(ControllerConstants.PASSWORD));
        registerApplicationService.unRegister(appId);

        JSONObject response = new JSONObject();
        response.put(ControllerConstants.UN_REGISTER, ControllerConstants.SUCCESS);
        return response;
    }

    private JSONObject register(JSONObject request) {
        validateAppIdAndPasswordFields(request);
        String appId = String.valueOf(request.get(ControllerConstants.APP_ID));
        String password = String.valueOf(request.get(ControllerConstants.PASSWORD));
        registerApplicationService.register(appId, password);
        JSONObject registeredApp = new JSONObject();
        registeredApp.put(ControllerConstants.APP_ID, appId);
        registeredApp.put(ControllerConstants.PASSWORD, password);
        return registeredApp;
    }

    private void validateAppIdAndPasswordFields(JSONObject request) {
        StringBuilder validationErrors = new StringBuilder();
        try {
            request.get(ControllerConstants.APP_ID);
        } catch (Exception e) {
            validationErrors.append(noFiledFound(ControllerConstants.APP_ID));
        }
        try {
            request.get(ControllerConstants.PASSWORD);
        } catch (Exception e) {
            validationErrors.append(noFiledFound(ControllerConstants.PASSWORD));
        }
        if (validationErrors.length() > 0) {
            throw new RuntimeException(validationErrors.toString());
        }
    }


    private String noFiledFound(String missingField) {
        return "No '" + missingField + "'found.";
    }

}
