package uk.gergely.kiss.configurationprovider.security.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.repository.RegisteredPasswordEntityRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredPasswordEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RegisteredPasswordEntityRepository passwordEntityRepository;


    public PasswordManagerServiceImpl() {
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
        return passwordEntityRepository.findAll().stream().map(RegisteredPasswordEntity::getPassword).collect(Collectors.toList());
    }
}
