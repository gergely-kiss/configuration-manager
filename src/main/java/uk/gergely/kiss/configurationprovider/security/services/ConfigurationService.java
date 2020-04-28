package uk.gergely.kiss.configurationprovider.security.services;

import uk.gergely.kiss.configurationprovider.repository.entity.ConfigurationEntity;

import java.util.List;

public interface ConfigurationService {
    List<ConfigurationEntity> getAllConfigurationByAppId(String appId);
    ConfigurationEntity getConfigByKeyAndAppId(String key, String appId);
    ConfigurationEntity saveConfiguration(String key, String value, String appId);
    void deleteConfig(String key, String appId);
}
