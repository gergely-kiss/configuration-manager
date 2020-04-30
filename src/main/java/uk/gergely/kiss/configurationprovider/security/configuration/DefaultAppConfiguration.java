package uk.gergely.kiss.configurationprovider.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gergely.kiss.configurationprovider.data.entities.AppEntity;
import uk.gergely.kiss.configurationprovider.security.resources.SecurityConstants;
import uk.gergely.kiss.configurationprovider.data.services.AppService;
import javax.persistence.NoResultException;

@Component
public class DefaultAppConfiguration {
    private final AppService appService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAppConfiguration.class);
    @Autowired
    public DefaultAppConfiguration(AppService appService) {
        this.appService = appService;
    }

    public void registerDefaultApplication(){
        AppEntity defaultApp;
          try{
              defaultApp = appService.findByApplicationId(SecurityConstants.DEFAULT_APP_NAME);
              printDefaultAppInfo(defaultApp);
          }  catch (NoResultException e){

              defaultApp = appService.register( SecurityConstants.DEFAULT_APP_NAME,
                                                                SecurityConstants.DEFAULT_PASSWORD,
                                                                SecurityConstants.ROLE_ADMIN);
              LOGGER.info("First run of the application");
              LOGGER.info("Default application registered");
              printDefaultAppInfo(defaultApp);


          }
    }

    private void printDefaultAppInfo(AppEntity defaultApp) {
        LOGGER.info("Application id: {}", defaultApp.getAppId());
        LOGGER.info("Application password: {}", SecurityConstants.DEFAULT_PASSWORD);
    }
}
