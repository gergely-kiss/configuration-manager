package uk.gergely.kiss.configurationprovider.data;

import java.util.List;

public interface AppInfoService {

    String encode(String appInfo);
    boolean isMatch(String appInfo, String encodedPassword);
    List<String> getAllPlanPassword();
    void savePlainPassword(String appInfo);

}
