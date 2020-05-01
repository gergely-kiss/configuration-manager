package uk.gergely.kiss.configurationprovider.security.resources;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityConstants {

    public static final String UN_AUTHORIZED = "Unauthorized to preform this operation";

    private SecurityConstants() {
    }
    public static final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    public static final String USER_DETAIL_SERVICE_QUALIFIER = "userDetailServiceImplementation";
    public static final String ROLE_APPLICATION = "ROLE_APPLICATION";
}
