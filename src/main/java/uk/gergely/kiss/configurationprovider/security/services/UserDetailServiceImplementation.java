package uk.gergely.kiss.configurationprovider.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.configurationprovider.security.vo.UserDetailsVO;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserDetailsVO(s);
    }
}
