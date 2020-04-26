package uk.gergely.kiss.configurationprovider.security.services;

import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;

import java.util.Set;

public interface RegisterApplicationService {

    RegisteredApplicationEntity register(String name);
    void unRegister(RegisteredApplicationEntity registeredApplicationEntity);
    RegisteredApplicationEntity findByApplicationId(String registeredApplicationId);
    Set<RegisteredApplicationEntity> findAll();
    RegisteredApplicationEntity updatePassword(RegisteredApplicationEntity registeredApplicationEntity, String newPassword);
    RegisteredApplicationEntity register(String defaultAppName, String defaultPassword);
}
