package uk.gergely.kiss.configurationprovider.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.security.vo.UserDetailsDTO;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    private final RegisteredApplicationService registeredApplicationService;

    @Autowired
    public UserDetailServiceImplementation(RegisteredApplicationService registeredApplicationService) {
        this.registeredApplicationService = registeredApplicationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsDTO(registeredApplicationService.findByApplicationId(username));
    }
}
