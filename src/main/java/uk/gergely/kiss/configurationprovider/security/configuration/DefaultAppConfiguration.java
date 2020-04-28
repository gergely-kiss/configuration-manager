package uk.gergely.kiss.configurationprovider.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;
import uk.gergely.kiss.configurationprovider.security.resources.SecurityConstants;
import uk.gergely.kiss.configurationprovider.security.services.RegisteredApplicationService;
import javax.persistence.NoResultException;

@Component
public class DefaultAppConfiguration {
    private final RegisteredApplicationService registeredApplicationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAppConfiguration.class);
    @Autowired
    public DefaultAppConfiguration(RegisteredApplicationService registeredApplicationService) {
        this.registeredApplicationService = registeredApplicationService;
    }

    public void registerDefaultApplication(){
        RegisteredApplicationEntity defaultApp;
          try{
              defaultApp = registeredApplicationService.findByApplicationId(SecurityConstants.DEFAULT_APP_NAME);
              printDefaultAppInfo(defaultApp);
          }  catch (NoResultException e){

              defaultApp = registeredApplicationService.register( SecurityConstants.DEFAULT_APP_NAME,
                                                                SecurityConstants.DEFAULT_PASSWORD,
                                                                SecurityConstants.ROLE_ADMIN);
              LOGGER.info("First run of the application");
              LOGGER.info("Default application registered");
              printDefaultAppInfo(defaultApp);


          }
    }

    private void printDefaultAppInfo(RegisteredApplicationEntity defaultApp) {
        LOGGER.info("Application id: {}", defaultApp.getApplicationId());
        LOGGER.info("Application password: {}", SecurityConstants.DEFAULT_PASSWORD);
    }
}
