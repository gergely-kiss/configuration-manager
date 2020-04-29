package uk.gergely.kiss.configurationprovider.data;

import java.util.List;

public interface AppInfoService {

    String encode(String plainPassword);
    boolean isMatch(String plainPassword, String encodedPassword);
    List<String> getAllPlanPassword();
    void savePlainPassword(String planPassword);

}
