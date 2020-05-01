package uk.gergely.kiss.configurationprovider.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gergely.kiss.configurationprovider.data.services.AppService;

import javax.persistence.NoResultException;

@Component
public class DefaultAppConfiguration {

    private final AppService appService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAppConfiguration.class);

    @Value("${spring.security.user.name}")
    private String defaultUser;
    @Value("${spring.security.user.password}")
    private String defaultAppInfo;
    @Value("${spring.security.user.role}")
    private String defaultRole;

    @Autowired
    public DefaultAppConfiguration(AppService appService) {
        this.appService = appService;
    }


    public void registerDefaultApplication() {
        try {
            appService.findByApplicationId(defaultUser);
            printDefaultAppInfo();
        } catch (NoResultException e) {
            appService.register(defaultUser,defaultAppInfo,defaultRole);
            LOGGER.info("First run of the application");
            LOGGER.info("Default application registered");
            printDefaultAppInfo();


        }
    }

    private void printDefaultAppInfo() {
        LOGGER.info("Application id: {}", defaultUser);
        LOGGER.info("Application appInfo: {}", defaultAppInfo);
        LOGGER.info("Application role: {}", defaultRole);
    }
}
