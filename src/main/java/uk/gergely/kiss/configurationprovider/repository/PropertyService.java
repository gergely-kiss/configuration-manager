package uk.gergely.kiss.configurationprovider.repository;

public interface PropertyService {

    void save (String propertyKey, String propertyValue, String appId);
}
