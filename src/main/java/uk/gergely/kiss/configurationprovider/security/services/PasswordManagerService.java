package uk.gergely.kiss.configurationprovider.security.services;

import java.util.List;

public interface PasswordManagerService {

    String encode(String plainPassword);
    boolean isMatch(String plainPassword, String encodedPassword);
    List<String> getAllPlanPassword();
    void savePlainPassword(String planPassword);

}
