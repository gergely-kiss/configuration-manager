package uk.gergely.kiss.configurationprovider.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppInfoServiceImpl implements AppInfoService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  final AppInfoEntityRepository repository;


    public AppInfoServiceImpl(AppInfoEntityRepository repository) {
        this.repository = repository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String appInfo) {
        return bCryptPasswordEncoder.encode(appInfo);
    }

    @Override
    public boolean isMatch(String appInfo, String encodedPassword) {
        return bCryptPasswordEncoder.matches(appInfo, encodedPassword);
    }

    @Override
    public List<String> getAllPlanPassword() {
        return repository.findAll().stream().map(AppInfoEntity::getAppInfo).collect(Collectors.toList());
    }

    @Override
    public void savePlainPassword(String appInfo) {

        AppInfoEntity passwordEntity = new AppInfoEntity();
        passwordEntity.setAppInfo(appInfo);
        repository.save(passwordEntity);
    }
}
