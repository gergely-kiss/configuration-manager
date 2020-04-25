package uk.gergely.kiss.configurationprovider.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRequest {

    private Map<String, String> configurations;

}
