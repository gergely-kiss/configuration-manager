package uk.gergely.kiss.configurationprovider.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.data.AppService;
import uk.gergely.kiss.configurationprovider.security.dto.UserDetailsDTO;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    private final AppService appService;

    @Autowired
    public UserDetailServiceImplementation(AppService appService) {
        this.appService = appService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserDetailsDTO(appService.findByApplicationId(username));
    }
}
