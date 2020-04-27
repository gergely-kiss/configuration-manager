package uk.gergely.kiss.configurationprovider.security.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;

import java.util.Arrays;
import java.util.Collection;

public class UserDetailsDTO implements UserDetails {
    private String userName;
    private String password;
    private String role;

    public UserDetailsDTO(String userName){
        this.userName = userName;
    }

    public UserDetailsDTO(RegisteredApplicationEntity registeredApplicationEntity) {
        this.userName = registeredApplicationEntity.getApplicationId();
        this.password = registeredApplicationEntity.getPassword();
        this.role = registeredApplicationEntity.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
