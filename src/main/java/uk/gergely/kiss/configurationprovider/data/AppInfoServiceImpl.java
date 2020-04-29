package uk.gergely.kiss.configurationprovider.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppInfoServiceImpl implements AppInfoService {
    private final Logger LOGGER = LoggerFactory.getLogger(AppInfoServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  final AppInfoEntityRepository repository;


    public AppInfoServiceImpl(AppInfoEntityRepository repository) {
        this.repository = repository;
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
        return repository.findAll().stream().map(AppInfoEntity::getAppInfo).collect(Collectors.toList());
    }

    @Override
    public void savePlainPassword(String planPassword) {

        AppInfoEntity passwordEntity = new AppInfoEntity();
        passwordEntity.setAppInfo(planPassword);
        repository.save(passwordEntity);
    }
}
