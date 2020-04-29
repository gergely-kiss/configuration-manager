package uk.gergely.kiss.configurationprovider.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public AppEntity register(String name, String password) {
        return register(name, password, SecurityConstants.ROLE_APPLICATION);
    }

    @Override
    public AppEntity register(String name, String password, String role) {
        if (isApplicationAlreadyRegistered(name)) {
            throw new KeyAlreadyExistsException();
        }
        appInfoService.savePlainPassword(password);
        return repository.save(new AppEntity(name, appInfoService.encode(password), role));
    }

    @Override
    public void unRegister(String appId) {
        repository.delete(findByApplicationId(appId));
    }

    @Override
    public AppEntity findByApplicationId(String registeredApplicationId) {
        return repository.findById(registeredApplicationId).orElseThrow(()-> new NoResultException("No application find by application name: " + registeredApplicationId));
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


    private boolean isApplicationAlreadyRegistered(String name) {
        return repository.findAll().stream().filter(registeredApplicationEntity -> name.equalsIgnoreCase(registeredApplicationEntity.getAppId())).findAny().isPresent();
    }

}
