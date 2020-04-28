package uk.gergely.kiss.configurationprovider.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.repository.RegisteredPasswordEntityRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredPasswordEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {
    private final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  final RegisteredPasswordEntityRepository passwordEntityRepository;


    public PasswordManagerServiceImpl(RegisteredPasswordEntityRepository passwordEntityRepository) {
        this.passwordEntityRepository = passwordEntityRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String plainPassword) {
        return bCryptPasswordEncoder.encode(plainPassword);
    }

    @Override
    public boolean isMatch(String plainPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(plainPassword, encodedPassword);
    }

    @Override
    public List<String> getAllPlanPassword() {
        return passwordEntityRepository.findAll().stream().map(RegisteredPasswordEntity::getApplicationInfo).collect(Collectors.toList());
    }

    @Override
    public void savePlainPassword(String planPassword) {

        RegisteredPasswordEntity passwordEntity = new RegisteredPasswordEntity();
        passwordEntity.setApplicationInfo(planPassword);
        passwordEntityRepository.save(passwordEntity);
    }
}
