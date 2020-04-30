package uk.gergely.kiss.configurationprovider.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyEntityRepository repository;
    private final AppService appService;
    public PropertyServiceImpl(PropertyEntityRepository repository, AppService appService) {
        this.repository = repository;
        this.appService = appService;
    }

    @Override
    public PropertyEntity save(String propertyKey, String propertyValue, String appId) {
        appService.findByApplicationId(appId);
        PropertyEntity propertyEntity;
        try {
            propertyEntity = findPropertyValueByAppIdAndPropertyKey(appId, propertyKey);
        } catch (NoResultException e) {
            propertyEntity = new PropertyEntity();
        }
        propertyEntity.setAppId(appId);
        propertyEntity.setPropertyKey(propertyKey);
        propertyEntity.setPropertyValue(propertyValue);
        return repository.save(propertyEntity);
    }

    @Override
    public void delete(String appId, String propertyKey) {
        repository.delete(findPropertyValueByAppIdAndPropertyKey(appId, propertyKey));
    }

    @Override
    public List<PropertyEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<PropertyEntity> findAllByAppId(String appId) {
        return findAll().stream().filter(p -> p.getAppId().equalsIgnoreCase(appId)).collect(Collectors.toList());
    }

    @Override
    public PropertyEntity findPropertyValueByAppIdAndPropertyKey(String appId, String propertyKey) {
        return findAll().stream().filter(p -> p.getAppId().equalsIgnoreCase(appId)
                && p.getPropertyKey().equalsIgnoreCase(propertyKey)).findFirst().orElseThrow(() -> new NoResultException("property not found"));
    }
}
