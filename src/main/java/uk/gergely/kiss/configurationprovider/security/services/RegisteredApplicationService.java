package uk.gergely.kiss.configurationprovider.security.services;

import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;

import java.util.Set;

public interface RegisteredApplicationService {

    RegisteredApplicationEntity register(String appId, String password);
    void unRegister(String appId);
    RegisteredApplicationEntity findByApplicationId(String appId);
    Set<RegisteredApplicationEntity> findAll();
    RegisteredApplicationEntity updatePassword(RegisteredApplicationEntity registeredApplicationEntity, String newPassword);
    RegisteredApplicationEntity register(String defaultAppName, String defaultPassword, String role);
}
