package uk.gergely.kiss.configurationprovider.security.services;

public interface PasswordManagerService {

    String encode(String plainPassword);
    boolean isMatch(String plainPassword, String encodedPassword);

}
