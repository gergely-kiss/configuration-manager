package uk.gergely.kiss.configurationprovider.data;
import java.util.Set;

public interface AppService {

    AppEntity register(String appId, String password);
    void unRegister(String appId);
    AppEntity findByApplicationId(String appId);
    Set<AppEntity> findAll();
    AppEntity updatePassword(AppEntity appEntity, String newPassword);
    AppEntity register(String defaultAppName, String defaultPassword, String role);
}
