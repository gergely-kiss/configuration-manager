package uk.gergely.kiss.configurationprovider.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;
import uk.gergely.kiss.configurationprovider.security.services.PasswordManagerService;
import uk.gergely.kiss.configurationprovider.security.services.RegisterApplicationService;

import javax.management.openmbean.KeyAlreadyExistsException;

@Component
public class DefaultAppConfiguration {
    private static final String DEFAULT_APP_NAME = "config-manager";
    private static final String DEFAULT_PASSWORD = "config_manager_password";
    private final RegisterApplicationService registerApplicationService;
    private final PasswordManagerService passwordManagerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAppConfiguration.class);
    @Autowired
    public DefaultAppConfiguration(RegisterApplicationService registerApplicationService, PasswordManagerService passwordManagerService) {
        this.registerApplicationService = registerApplicationService;
        this.passwordManagerService = passwordManagerService;
    }

    public void registerDefaultApplication(){
        RegisteredApplicationEntity defaultApp = null;
          try{
              registerApplicationService.register(DEFAULT_APP_NAME, DEFAULT_PASSWORD);
              LOGGER.info("First run of the application");
              LOGGER.info("Default application registered");
              printDefaultAppInfo();
          }  catch (KeyAlreadyExistsException e){
              LOGGER.info("Default application info");
              printDefaultAppInfo();

          }
    }

    private void printDefaultAppInfo() {
        LOGGER.info("Application id: {}", DEFAULT_APP_NAME);
        LOGGER.info("Application password: {}", DEFAULT_PASSWORD);
    }

}
