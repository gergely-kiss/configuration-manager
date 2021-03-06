package uk.gergely.kiss.configurationprovider.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.gergely.kiss.configurationprovider.data.entities.AppEntity;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsDTO implements UserDetails {
    private String userName;
    private String password;
    private String role;

    public UserDetailsDTO(String userName){
        this.userName = userName;
    }

    public UserDetailsDTO(AppEntity appEntity) {
        this.userName = appEntity.getAppId();
        this.password = appEntity.getAppInfo();
        this.role = appEntity.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
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
