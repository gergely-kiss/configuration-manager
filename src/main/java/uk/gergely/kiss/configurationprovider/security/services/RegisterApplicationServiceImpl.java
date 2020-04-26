package uk.gergely.kiss.configurationprovider.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.repository.RegisteredApplicationEntityRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class RegisterApplicationServiceImpl implements RegisterApplicationService {

    private final RegisteredApplicationEntityRepository registeredApplicationEntityRepository;
    private final PasswordManagerService passwordManagerService;

    @Autowired
    public RegisterApplicationServiceImpl(RegisteredApplicationEntityRepository registeredApplicationEntityRepository, PasswordManagerService passwordManagerService) {
        this.registeredApplicationEntityRepository = registeredApplicationEntityRepository;
        this.passwordManagerService = passwordManagerService;
    }

    @Override
    public RegisteredApplicationEntity register(String name) {
        return register(name, passwordManagerService.encode(UUID.randomUUID().toString()));
    }

    @Override
    public RegisteredApplicationEntity register(String name, String password) {
        if (isApplicationAlreadyRegistered(name)) {
            throw new KeyAlreadyExistsException();
        }
        return registeredApplicationEntityRepository.save(new RegisteredApplicationEntity(name, passwordManagerService.encode(password)));
    }

    @Override
    public void unRegister(RegisteredApplicationEntity registeredApplicationEntity) {
        registeredApplicationEntityRepository.delete(registeredApplicationEntity);
    }

    @Override
    public RegisteredApplicationEntity findByApplicationId(String registeredApplicationId) {
        return registeredApplicationEntityRepository.findById(registeredApplicationId).orElseThrow(()-> new NoResultException("No application find by application name: " + registeredApplicationId));
    }

    @Override
    public Set<RegisteredApplicationEntity> findAll() {
        return new HashSet<>(registeredApplicationEntityRepository.findAll());
    }

    @Override
    public RegisteredApplicationEntity updatePassword(RegisteredApplicationEntity registeredApplicationEntity, String newPassword) {
        RegisteredApplicationEntity savedApplicationEntity = findByApplicationId(registeredApplicationEntity.getApplicationId());
        savedApplicationEntity.setPassword(passwordManagerService.encode(newPassword));
        return registeredApplicationEntityRepository.save(savedApplicationEntity);

    }


    private boolean isApplicationAlreadyRegistered(String name) {
        return registeredApplicationEntityRepository.findAll().stream().filter(registeredApplicationEntity -> name.equalsIgnoreCase(registeredApplicationEntity.getApplicationId())).findAny().isPresent();
    }

}
