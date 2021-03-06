package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.ConfigurationManagementRuntimeException;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;
import uk.gergely.kiss.configurationprovider.data.services.PropertyService;
import uk.gergely.kiss.configurationprovider.data.services.AppInfoService;
import uk.gergely.kiss.configurationprovider.data.services.AppService;
import uk.gergely.kiss.configurationprovider.security.dto.UserDetailsDTO;
import uk.gergely.kiss.configurationprovider.security.resources.SecurityConstants;

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
    public JSONObject operate(String operationId, org.json.simple.JSONObject request, UserDetailsDTO userDetailsDTO) {

        if (operationId.equalsIgnoreCase(ControllerConstants.REGISTER)) {
            isAuthorized(userDetailsDTO);
            return register(request);
        } else if (operationId.equalsIgnoreCase(ControllerConstants.UN_REGISTER)) {
            isAuthorized(userDetailsDTO);
            return unRegister(request);
        } else if (operationId.equalsIgnoreCase(ControllerConstants.RETURN_ALL_APP)) {
            isAuthorized(userDetailsDTO);
            return returnAllApp();
        } else if (operationId.equalsIgnoreCase(ControllerConstants.RETURN_ALL_APP_WITH_ALL_PROPERTY)) {
            isAuthorized(userDetailsDTO);
            return returnAllAppWithAllProperty();
        } else if (operationId.equalsIgnoreCase(ControllerConstants.UPDATE_PASSWORD)) {
            isAuthorized(userDetailsDTO, String.valueOf(request.get(ControllerConstants.APP_ID)));
            return updatePassword(request);
        } else if (operationId.equalsIgnoreCase(ControllerConstants.SAVE_PROPERTY)) {
            isAuthorized(userDetailsDTO, String.valueOf(request.get(ControllerConstants.APP_ID)));
            return saveProperty(request);
        } else if (operationId.equalsIgnoreCase(ControllerConstants.REMOVE_PROPERTY)) {
            isAuthorized(userDetailsDTO, String.valueOf(request.get(ControllerConstants.APP_ID)));
            return removeProperty(request);
        } else if (operationId.equalsIgnoreCase(ControllerConstants.RETURN_ALL_PROPERTY)) {
            isAuthorized(userDetailsDTO, String.valueOf(request.get(ControllerConstants.APP_ID)));
            return returnAllProperty(request);
        }

        JSONObject operationOptions = new JSONObject();
        operationOptions.put(ControllerConstants.REGISTER, "Register new application. Required keys: " + ControllerConstants.APP_ID + " " + ControllerConstants.APP_INFO);
        operationOptions.put(ControllerConstants.UN_REGISTER, "Remove an already existing application. Required keys:" + ControllerConstants.APP_ID + " " + ControllerConstants.APP_INFO + "(existing password)." + " Warning: this operation will remove every already existing property related to that application too!");
        operationOptions.put(ControllerConstants.RETURN_ALL_APP, "Get all registered applications. Request body not processed.");
        operationOptions.put(ControllerConstants.RETURN_ALL_APP_WITH_ALL_PROPERTY, "Get all registered applications and related properties.  Request body not processed.");
        operationOptions.put(ControllerConstants.UPDATE_PASSWORD, "Change the password. Required keys:" + ControllerConstants.APP_ID + " " + ControllerConstants.APP_INFO + "(existing password) " + ControllerConstants.UPDATE_PASSWORD + " (new password)");
        operationOptions.put(ControllerConstants.SAVE_PROPERTY, "Add new or update already existing property. Required keys: " + ControllerConstants.APP_ID + " " + ControllerConstants.PROPERTY_KEY + " " + ControllerConstants.PROPERTY_VALUE);
        operationOptions.put(ControllerConstants.REMOVE_PROPERTY, "Remove already existing property. Required keys: " + ControllerConstants.APP_ID + " " + ControllerConstants.PROPERTY_KEY);
        operationOptions.put(ControllerConstants.RETURN_ALL_PROPERTY, "Return all already existing property. Required keys: " + ControllerConstants.APP_ID);
        JSONObject response = new JSONObject();
        response.put("Error", "No valid operation find by operation id (" + operationId + ") please use a valid operation id");
        response.put("Operation Options", operationOptions);
        return response;

    }

    private void isAuthorized(UserDetailsDTO userDetailsDTO, String appId) {
        if (!userDetailsDTO.getUsername().equalsIgnoreCase(appId)) {
            throw new ConfigurationManagementRuntimeException(userDetailsDTO.getUsername() + " is " + SecurityConstants.UN_AUTHORIZED + " for " + appId);
        }
    }

    private void isAuthorized(UserDetailsDTO userDetailsDTO) {
        if (!userDetailsDTO.getAuthorities().contains(SecurityConstants.ROLE_ADMIN)) {
            throw new ConfigurationManagementRuntimeException(SecurityConstants.UN_AUTHORIZED);
        }
    }

    private JSONObject register(org.json.simple.JSONObject request) {
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

    private JSONObject unRegister(org.json.simple.JSONObject request) {
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
            appInfoService.getAllAppInfo().stream().filter(plainPassword -> appInfoService.isMatch(plainPassword, registeredApplicationEntity.getAppInfo())).findFirst().ifPresent(plainPassword ->
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

    private JSONObject updatePassword(org.json.simple.JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.APP_INFO);
        validatePropertyKey(request, ControllerConstants.UPDATE_PASSWORD);
        appService.updatePassword(appService.findByApplicationId(String.valueOf(request.get(ControllerConstants.PROPERTY_KEY))), String.valueOf(request.get(ControllerConstants.UPDATE_PASSWORD)));
        JSONObject response = new JSONObject();
        response.put("update password ", ControllerConstants.PROCESSED);
        return response;
    }

    public JSONObject saveProperty(org.json.simple.JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PROPERTY_KEY);
        validatePropertyKey(request, ControllerConstants.PROPERTY_VALUE);
        JSONObject response = new JSONObject();
        propertyService.save(String.valueOf(request.get(ControllerConstants.PROPERTY_KEY)),
                String.valueOf(request.get(ControllerConstants.PROPERTY_VALUE)),
                String.valueOf(request.get(ControllerConstants.APP_ID)));
        response.put("saving property", ControllerConstants.PROCESSED);
        return response;
    }

    private JSONObject removeProperty(org.json.simple.JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PROPERTY_KEY);
        JSONObject response = new JSONObject();
        propertyService.delete(String.valueOf(request.get(ControllerConstants.APP_ID)), String.valueOf(request.get(ControllerConstants.PROPERTY_KEY)));
        response.put("removing config", ControllerConstants.PROCESSED);
        return response;
    }

    private JSONObject returnAllProperty(org.json.simple.JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        JSONObject response = new JSONObject();
        response.put(String.valueOf(request.get(ControllerConstants.APP_ID)), propertyService.findAllByAppId(String.valueOf(request.get(ControllerConstants.APP_ID))));
        return response;
    }

    private void validatePropertyKey(org.json.simple.JSONObject request, String propertyKey) {
        StringBuilder validationErrors = new StringBuilder();
        try {
            request.get(propertyKey);
        } catch (Exception e) {
            validationErrors.append(noFiledFound(propertyKey));
        }
        if (validationErrors.length() > 0) {
            throw new ConfigurationManagementRuntimeException(validationErrors.toString());
        }
    }

    private String noFiledFound(String missingField) {
        return "No '" + missingField + "'found.";
    }

}
