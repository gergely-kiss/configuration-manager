package uk.gergely.kiss.configurationprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.gergely.kiss.configurationprovider.security.configuration.DefaultAppConfiguration;

@EnableSwagger2
@SpringBootApplication
public class ConfigurationProviderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ConfigurationProviderApplication.class, args);
		context.getBean(DefaultAppConfiguration.class).registerDefaultApplication();
	}

}
