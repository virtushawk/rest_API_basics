package com.epam.esm.filter;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * The purpose of this filter is to create a user if it does not exist in the database.
 * If an Authentication object exists, it checks if the user exists in the database, and if not, it creates a new user.
 */
@RequiredArgsConstructor
public class UserFilter extends GenericFilterBean {

    private final UserService userService;

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication)) {
            KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            AccessToken idToken = principal.getKeycloakSecurityContext().getToken();
            UserDTO userDTO = UserDTO.builder().name(idToken.getPreferredUsername()).build();
            userService.create(userDTO);
        }
        chain.doFilter(request, response);
    }
}

