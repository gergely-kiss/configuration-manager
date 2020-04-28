package uk.gergely.kiss.configurationprovider.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gergely.kiss.configurationprovider.repository.ConfigurationEntityRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.ConfigurationEntity;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
    private final ConfigurationEntityRepository configurationEntityRepository;

    public ConfigurationServiceImpl(ConfigurationEntityRepository configurationEntityRepository) {
        this.configurationEntityRepository = configurationEntityRepository;
    }

    @Override
    public List<ConfigurationEntity> getAllConfigurationByAppId(String appId) {
        return configurationEntityRepository.findAll().stream()
                .filter(config -> config.getApplicationId().equalsIgnoreCase(appId))
                .collect(Collectors.toList());
    }

    @Override
    public ConfigurationEntity getConfigByKeyAndAppId(String key, String appId) {
        return configurationEntityRepository.findAll().stream()
                .filter(config -> config.getKey().equalsIgnoreCase(key) &&
                        config.getApplicationId().equalsIgnoreCase(appId)).findFirst()
                .orElseThrow(() -> new NoResultException("No config found with key:" + key + "for application id:" + appId));

    }

    @Override
    @Transactional
    public ConfigurationEntity saveConfiguration(String key, String value, String appId) {
        ConfigurationEntity configurationEntity;
        try {
            configurationEntity = getConfigByKeyAndAppId(key, appId);
        } catch (NoResultException e) {
            configurationEntity = new ConfigurationEntity();
        }
        configurationEntity.setApplicationId(appId);
        configurationEntity.setKey(key);
        configurationEntity.setValue(value);
        LOGGER.info("!!!!!!!!!!!!!!!!!!! {}",configurationEntity);
        return configurationEntityRepository.save(configurationEntity);

    }

    @Override
    @Transactional
    public void deleteConfig(String key, String appId) {
        configurationEntityRepository.delete(getConfigByKeyAndAppId(key, appId));
    }
}
