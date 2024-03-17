package com.nnk.springboot.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

/**
 * Spring component to redirect user after success login.
 * Called by the SecurityFilterChain in the SpringSecurityConfig.
 */
@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    /**
     * Method called when a user has been successfully authentication.
     * Get the GrantedAuthority of the Principal.
     * redirect the principal from the login page to the home page of his role.
     *
     * @param request        the request which caused the successful authentication.
     * @param response       the response.
     * @param authentication representation of Principal.
     * @throws IOException can be lifted by sendRedirect
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for(GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            switch(authority) {
                case "ROLE_ADMIN" -> redirectStrategy.sendRedirect(request, response, "/");
                case "ROLE_USER" -> redirectStrategy.sendRedirect(request, response, "/bidList/list");
                default -> throw new IllegalStateException();
            }
        }
    }
}
