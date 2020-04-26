package uk.gergely.kiss.configurationprovider.swagger.configuration;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import uk.gergely.kiss.configurationprovider.controllers.resources.ControllerConstants;

import static springfox.documentation.builders.PathSelectors.regex;

public class SwaggerConfiguration {

    @Bean
    public Docket apiRoot() {
        return new Docket(DocumentationType.SWAGGER_2).select().paths(paths()).apis(RequestHandlerSelectors.any())
                .build().apiInfo(getApiInfo()).useDefaultResponseMessages(false);
    }

    private Predicate<String> paths() {
        return regex(ControllerConstants.API_ROOT + ControllerConstants.REGEX_ALL);
    }

    private Contact getContactDetails() {
        return new Contact(ControllerConstants.CONTACT_NAME, ControllerConstants.CONTACT_URL,
                ControllerConstants.CONTACT_EMAIL);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title(ControllerConstants.TRAINING_SERVICE_TITLE)
                .version(ControllerConstants.TRAINING_SERVICE_VERSION).contact(getContactDetails()).build();
    }
}
