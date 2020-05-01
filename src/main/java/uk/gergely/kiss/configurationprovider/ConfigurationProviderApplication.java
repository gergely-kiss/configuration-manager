package uk.gergely.kiss.configurationprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.gergely.kiss.configurationprovider.security.configuration.DefaultAppConfiguration;

@SpringBootApplication
public class ConfigurationProviderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigurationProviderApplication.class, args);
        context.getBean(DefaultAppConfiguration.class).registerDefaultApplication();
    }

}
