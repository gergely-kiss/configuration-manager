package uk.gergely.kiss.configurationprovider.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyServiceImpl.class);
    private final PropertyEntityRepository repository;

    public PropertyServiceImpl(PropertyEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(String propertyKey, String propertyValue, String appId) {
        LOG.info("-->");
        LOG.info("property key {} property value {} app id {}", propertyKey, propertyValue, appId);
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setAppId(appId);
        propertyEntity.setPropertyKey(propertyKey);
        propertyEntity.setPropertyValue(propertyValue);
        PropertyEntity save = repository.save(propertyEntity);
        LOG.info("saved {} ", save);

    }
}
