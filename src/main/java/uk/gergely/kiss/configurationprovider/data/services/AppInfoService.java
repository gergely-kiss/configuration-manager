package uk.gergely.kiss.configurationprovider.data.services;

import java.util.List;

public interface AppInfoService {

    String encode(String appInfo);
    boolean isMatch(String appInfo, String encodedPassword);
    List<String> getAllAppInfo();
    void saveAppInfo(String appInfo);

}
