package uk.gergely.kiss.configurationprovider.controllers.services;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;
import uk.gergely.kiss.configurationprovider.repository.PropertyService;
import uk.gergely.kiss.configurationprovider.security.services.PasswordManagerService;
import uk.gergely.kiss.configurationprovider.security.services.RegisteredApplicationService;


@Service
public class APIOperationsServiceImpl implements APIOperationsService {

    private final RegisteredApplicationService registeredApplicationService;
    private final PasswordManagerService passwordManagerService;
    private final PropertyService propertyService;

    private final Logger LOGGER = LoggerFactory.getLogger(APIOperationsService.class);
    public APIOperationsServiceImpl(RegisteredApplicationService registeredApplicationService, PasswordManagerService passwordManagerService, PropertyService propertyService) {
        this.registeredApplicationService = registeredApplicationService;
        this.passwordManagerService = passwordManagerService;
        this.propertyService = propertyService;
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
                updatePassword(request);
            case ControllerConstants.ADD_UPDATE_CONFIG:
                saveConfig(request);
            case ControllerConstants.REMOVE_CONFIG:
                return removeConfig(request);
            case ControllerConstants.RETURN_ALL_CONFIG:
                return getAllConfig(request);
        }
        JSONObject operationOptions = new JSONObject();
        operationOptions.put(ControllerConstants.ADD_UPDATE_CONFIG, "Add new or update already existing configuration");
        operationOptions.put(ControllerConstants.REMOVE_CONFIG, "Remove already existing configuration");
        operationOptions.put(ControllerConstants.RETURN_ALL_CONFIG, "Return all already existing configuration");
        operationOptions.put(ControllerConstants.REGISTER, "Register new application");
        operationOptions.put(ControllerConstants.UN_REGISTER, "Remove an already existing application. Warning: this operation will remove every already existing configuration relating to that application too!");
        operationOptions.put(ControllerConstants.RETURN_ALL_APP, "Get all registered applications");
        operationOptions.put(ControllerConstants.RETURN_ALL_APP_WITH_ALL_CONFIG, "Get all registered applications and related configurations");
        operationOptions.put(ControllerConstants.UPDATE_PASSWORD, "Change the password");
        JSONObject response = new JSONObject();
        response.put("Error", "No valid operation find by operation id (" + operationId + ") please use a valid operation id");
        response.put("Operation Options", operationOptions);
        return response;
    }

    private JSONObject getAllConfig(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        JSONObject response = new JSONObject();
       // response.put(String.valueOf(request.get(ControllerConstants.APP_ID)), configurationService.getAllConfigurationByAppId(String.valueOf(request.get(ControllerConstants.APP_ID))));
        return response;
    }

    private JSONObject removeConfig(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.CONFIGURATION_KEY);
        //configurationService.deleteConfig(String.valueOf(request.get(ControllerConstants.CONFIGURATION_KEY)), String.valueOf(request.get(ControllerConstants.APP_ID)));
        return new JSONObject();
    }

    public void saveConfig(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.CONFIGURATION_KEY);
        validatePropertyKey(request, ControllerConstants.CONFIGURATION_VALUE);
        LOGGER.info("String.valueOf(request.get(ControllerConstants.CONFIGURATION_KEY) {}", String.valueOf(request.get(ControllerConstants.CONFIGURATION_KEY)));
        LOGGER.info("String.valueOf(request.get(ControllerConstants.CONFIGURATION_VALUE)) {}", String.valueOf(request.get(ControllerConstants.CONFIGURATION_VALUE)));
        LOGGER.info("String.valueOf(request.get(ControllerConstants.APP_ID)) {}", String.valueOf(request.get(ControllerConstants.APP_ID)));
        LOGGER.info("saveConfig {}", request);
        propertyService.save(String.valueOf(request.get(ControllerConstants.CONFIGURATION_KEY)), String.valueOf(request.get(ControllerConstants.CONFIGURATION_VALUE)), String.valueOf(request.get(ControllerConstants.APP_ID)));
    }

    private void updatePassword(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PASSWORD);
        validatePropertyKey(request, ControllerConstants.UPDATE_PASSWORD);
        registeredApplicationService.updatePassword(registeredApplicationService.findByApplicationId(String.valueOf(request.get(ControllerConstants.CONFIGURATION_KEY))), String.valueOf(request.get(ControllerConstants.UPDATE_PASSWORD)));
    }

    private JSONObject getAllAppAndAllConfig() {
        JSONObject response = new JSONObject();
        registeredApplicationService.findAll().forEach(registeredApplicationEntity -> {
            JSONObject app = new JSONObject();
            app.put(ControllerConstants.APP_ID, registeredApplicationEntity.getApplicationId());
       //     app.put(ControllerConstants.CONFIGURATION_LIST, configurationService.getAllConfigurationByAppId(registeredApplicationEntity.getApplicationId()));
            response.put(registeredApplicationEntity.getApplicationId(), app);
        });
        return response;
    }

    private JSONObject getAllApp() {
        JSONObject response = new JSONObject();

        registeredApplicationService.findAll().forEach(registeredApplicationEntity -> {
            JSONObject company = new JSONObject();
            company.put(ControllerConstants.APP_ID, registeredApplicationEntity.getApplicationId());
            passwordManagerService.getAllPlanPassword().stream().filter(plainPassword -> passwordManagerService.isMatch(plainPassword, registeredApplicationEntity.getPassword())).findFirst().ifPresent(plainPassword -> {
                company.put(ControllerConstants.PASSWORD, plainPassword);
            });
            response.put(registeredApplicationEntity.getApplicationId(), company);
        });
        return response;
    }

    private JSONObject unRegister(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PASSWORD);
        String appId = String.valueOf(request.get(ControllerConstants.APP_ID));
        registeredApplicationService.unRegister(appId);

        JSONObject response = new JSONObject();
        response.put(ControllerConstants.UN_REGISTER, ControllerConstants.SUCCESS);
        return response;
    }

    private JSONObject register(JSONObject request) {
        validatePropertyKey(request, ControllerConstants.APP_ID);
        validatePropertyKey(request, ControllerConstants.PASSWORD);
        String appId = String.valueOf(request.get(ControllerConstants.APP_ID));
        String password = String.valueOf(request.get(ControllerConstants.PASSWORD));
        registeredApplicationService.register(appId, password);
        JSONObject registeredApp = new JSONObject();
        registeredApp.put(ControllerConstants.APP_ID, appId);
        registeredApp.put(ControllerConstants.PASSWORD, password);
        return registeredApp;
    }

    private void validatePropertyKey(JSONObject request, String propertyKey) {
        StringBuilder validationErrors = new StringBuilder();
        try {
            request.get(propertyKey);
        } catch (Exception e) {
            validationErrors.append(noFiledFound(propertyKey));
        }

        if(validationErrors.length() >0){
            LOGGER.error(validationErrors.toString());
            throw new RuntimeException(validationErrors.toString());
        }
    }


    private String noFiledFound(String missingField) {
        return "No '" + missingField + "'found.";
    }

}
