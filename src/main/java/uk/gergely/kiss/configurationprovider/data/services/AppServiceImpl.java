package uk.gergely.kiss.configurationprovider.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.data.entities.AppEntity;
import uk.gergely.kiss.configurationprovider.data.repositories.AppEntityRepository;
import uk.gergely.kiss.configurationprovider.security.resources.SecurityConstants;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppServiceImpl implements AppService {

    private final AppEntityRepository repository;
    private final AppInfoService appInfoService;

    @Autowired
    public AppServiceImpl(AppEntityRepository repository, AppInfoService appInfoService) {
        this.repository = repository;
        this.appInfoService = appInfoService;
    }

    @Override
    public AppEntity register(String appID, String appInfo) {
        return register(appID, appInfo, SecurityConstants.ROLE_APPLICATION);
    }

    @Override
    public AppEntity register(String appID, String appInfo, String role) {
        if (isApplicationAlreadyRegistered(appID)) {
            throw new KeyAlreadyExistsException();
        }
        appInfoService.saveAppInfo(appInfo);
        return repository.save(new AppEntity(appID, appInfoService.encode(appInfo), role));
    }

    @Override
    public void unRegister(String appId) {
        repository.delete(findByApplicationId(appId));
    }

    @Override
    public AppEntity findByApplicationId(String appID) {
        return repository.findById(appID).orElseThrow(()-> new NoResultException("No application find by application name: " + appID));
    }

    @Override
    public Set<AppEntity> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public AppEntity updatePassword(AppEntity appEntity, String newPassword) {
        AppEntity savedApplicationEntity = findByApplicationId(appEntity.getAppId());
        savedApplicationEntity.setAppInfo(appInfoService.encode(newPassword));
        return repository.save(savedApplicationEntity);

    }


    private boolean isApplicationAlreadyRegistered(String appName) {
        return repository.findAll().stream().anyMatch(registeredApplicationEntity -> appName.equalsIgnoreCase(registeredApplicationEntity.getAppId()));
    }

}
