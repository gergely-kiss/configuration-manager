package uk.gergely.kiss.configurationprovider.data.services;
import uk.gergely.kiss.configurationprovider.data.entities.AppEntity;

import java.util.Set;

public interface AppService {

    AppEntity register(String appId, String appInfo);
    void unRegister(String appId);
    AppEntity findByApplicationId(String appId);
    Set<AppEntity> findAll();
    AppEntity updatePassword(AppEntity appEntity, String newPassword);
    AppEntity register(String appName, String appInfo, String role);
}
