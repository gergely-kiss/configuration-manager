package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;
import uk.gergely.kiss.configurationprovider.data.PropertyService;
import uk.gergely.kiss.configurationprovider.data.AppInfoService;
import uk.gergely.kiss.configurationprovider.data.AppService;

@Service
public class APIOperationsServiceImpl implements APIOperationsService {

    private final AppService appService;
    private final AppInfoService appInfoService;
    private final PropertyService propertyService;

    public APIOperationsServiceImpl(AppService appService, AppInfoService appInfoService, PropertyService propertyService) {
        this.appService = appService;
        this.appInfoService = appInfoService;
        this.propertyService = propertyService;
    }

    @Override
    public JSONObject operate(String operationId, JSONObject request) {
        if (operationId.equalsIgnoreCase(ControllerConstants.REGISTER))
            return register(request);
        else if (operationId.equalsIgnoreCase(ControllerConstants.UN_REGISTER))
            return unRegister(request);
        else if (operationId.equalsIgnoreCase(ControllerConstants.RETURN_ALL_APP))
            return returnAllApp();
        else if (operationId.equalsIgnoreCase(ControllerConstants.RETURN_ALL_APP_WITH_ALL_PROPERTY))
            return returnAllAppWithAllProperty();
        else if (operationId.equalsIgnoreCase(ControllerConstants.UPDATE_PASSWORD))
            return updatePassword(request);
        else if (operationId.equalsIgnoreCase(ControllerConstants.SAVE_PROPERTY))
            return saveProperty(request);
        else if (operationId.equalsIgnoreCase(ControllerConstants.REMOVE_PROPERTY))
            return removeProperty(request);
        else if (operationId.equalsIgnoreCase(ControllerConstants.RETURN_ALL_PROPERTY))
            return returnAllProperty(request);
        JSONObject operationOptions = new JSONObject();
        operationOptions.put(ControllerConstants.REGISTER, "Register new application. Required keys: " +ControllerConstants.APP_ID + " " + ControllerConstants.APP_INFO + "(existing password)");
        operationOptions.put(ControllerConstants.UN_REGISTER, "Remove an already existing application. Required keys:"+ControllerConstants.APP_ID + " " + ControllerConstants.APP_INFO +"(existing password)."+" Warning: this operation will remove every already existing property related to that application too!");
        operationOptions.put(ControllerConstants.RETURN_ALL_APP, "Get all registered applications. Request body not processed.");
        operationOptions.put(ControllerConstants.RETURN_ALL_APP_WITH_ALL_PROPERTY, "Get all registered applications and related properties.  Request body not processed.");
        operationOptions.put(ControllerConstants.UPDATE_PASSWORD, "Change the password. Required keys:" +ControllerConstants.APP_ID + " " + ControllerConstants.APP_INFO + "(existing password) " + ControllerConstants.UPDATE_PASSWORD + " (new password)");
        operationOptions.put(ControllerConstants.SAVE_PROPERTY, "Add new or update already existing property. Required keys: " +ControllerConstants.APP_ID + " " + ControllerConstants.PROPERTY_KEY + " " + ControllerConstants.PROPERTY_VALUE);
        operationOptions.put(ControllerConstants.REMOVE_PROPERTY, "Remove already existing property. Required keys: " +ControllerConstants.APP_ID + " " + ControllerConstants.PROPERTY_KEY);
        operationOptions.put(ControllerConstants.RETURN_ALL_PROPERTY, "Return all already existing property. Required keys: " + ControllerConstants.APP_ID);
        JSONObject response = new JSONObject();
        response.put("Error", "No valid operation find by operation id (" + operationId + ") please use a valid operation id");
        response.put("Operation Options", operationOptions);
        return response;

    }

    private JSONObject register(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.APP_INFO);
        String appId = String.valueOf(request.get(ControllerConstants.APP_ID));
        String password = String.valueOf(request.get(ControllerConstants.APP_INFO));
        appService.register(appId, password);
        JSONObject registeredApp = new JSONObject();
        registeredApp.put(ControllerConstants.APP_ID, appId);
        registeredApp.put(ControllerConstants.APP_INFO, password);
        return registeredApp;
    }

    private JSONObject unRegister(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.APP_INFO);
        String appId = String.valueOf(request.get(ControllerConstants.APP_ID));
        appService.unRegister(appId);
        JSONObject response = new JSONObject();
        response.put(ControllerConstants.UN_REGISTER, ControllerConstants.SUCCESS);
        return response;
    }

    private JSONObject returnAllApp() {
        JSONObject response = new JSONObject();
        appService.findAll().forEach(registeredApplicationEntity -> {
            JSONObject company = new JSONObject();
            company.put(ControllerConstants.APP_ID, registeredApplicationEntity.getAppId());
            appInfoService.getAllPlanPassword().stream().filter(plainPassword -> appInfoService.isMatch(plainPassword, registeredApplicationEntity.getAppInfo())).findFirst().ifPresent(plainPassword ->
                company.put(ControllerConstants.APP_INFO, plainPassword));
            response.put(registeredApplicationEntity.getAppId(), company);
        });
        return response;
    }

    private JSONObject returnAllAppWithAllProperty() {
        JSONObject response = new JSONObject();
        appService.findAll().forEach(registeredApplicationEntity -> {
            JSONObject app = new JSONObject();
            app.put(ControllerConstants.APP_ID, registeredApplicationEntity.getAppId());
            app.put(ControllerConstants.CONFIGURATION_LIST, propertyService.findAllByAppId(registeredApplicationEntity.getAppId()));
            response.put(registeredApplicationEntity.getAppId(), app);
        });
        return response;
    }

    private JSONObject updatePassword(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.APP_INFO);
        validatePropertyKey(request, ControllerConstants.UPDATE_PASSWORD);
        appService.updatePassword(appService.findByApplicationId(String.valueOf(request.get(ControllerConstants.PROPERTY_KEY))), String.valueOf(request.get(ControllerConstants.UPDATE_PASSWORD)));
        JSONObject response = new JSONObject();
        response.put("update password ", "processed" );
        return response;
    }

    public JSONObject saveProperty(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PROPERTY_KEY);
        validatePropertyKey(request, ControllerConstants.PROPERTY_VALUE);
        JSONObject response = new JSONObject();
        propertyService.save(String.valueOf(request.get(ControllerConstants.PROPERTY_KEY)),
                String.valueOf(request.get(ControllerConstants.PROPERTY_VALUE)),
                String.valueOf(request.get(ControllerConstants.APP_ID)));
        response.put("saving config", "processed");
        return response;
    }

    private JSONObject removeProperty(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PROPERTY_KEY);
        propertyService.delete(String.valueOf(request.get(ControllerConstants.APP_ID)), String.valueOf(request.get(ControllerConstants.PROPERTY_KEY)));
        return new JSONObject();
    }

    private JSONObject returnAllProperty(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        JSONObject response = new JSONObject();
        response.put(String.valueOf(request.get(ControllerConstants.APP_ID)), propertyService.findAllByAppId(String.valueOf(request.get(ControllerConstants.APP_ID))));
        return response;
    }

    private void validatePropertyKey(JSONObject request, String propertyKey) {
        StringBuilder validationErrors = new StringBuilder();
        try {
            request.get(propertyKey);
        } catch (Exception e) {
            validationErrors.append(noFiledFound(propertyKey));
        }
        if (validationErrors.length() > 0) {
            throw new RuntimeException(validationErrors.toString());
        }
    }

    private String noFiledFound(String missingField) {
        return "No '" + missingField + "'found.";
    }

}
