package uk.gergely.kiss.configurationprovider.data.services;

import uk.gergely.kiss.configurationprovider.data.entities.PropertyEntity;

import java.util.List;

public interface PropertyService {

    PropertyEntity save (String propertyKey, String propertyValue, String appId);
    void delete(String appId, String propertyKey);
    List<PropertyEntity> findAll();
    List<PropertyEntity> findAllByAppId(String appId);
    PropertyEntity findPropertyValueByAppIdAndPropertyKey(String appId, String propertyKey);
}
