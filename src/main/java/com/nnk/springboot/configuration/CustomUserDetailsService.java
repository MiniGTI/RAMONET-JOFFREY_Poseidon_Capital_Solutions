package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class to manage the UserDetailsService.
 */

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Method called by loadUserByUsername SpringSecurityConfig method.
     * If a user is found, create a new UserDetails with the user's parameters.
     * The userDetails role is build by the getGrantedAuthorities.
     *
     * @param username of the user who wishes to authenticate.
     * @return a UserDetails to become the Principal.
     * @throws UsernameNotFoundException -if Optional<User> is empty.
     * @see #getGrantedAuthorities(String role)
     * @see SpringSecurityConfig#authenticationManager(HttpSecurity, BCryptPasswordEncoder)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);

        User user = null;
        UserDetails log = null;

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        } else {
            user = optUser.get();

            log = new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                    getGrantedAuthorities(user.getRole()));
        }
        return log;
    }

    /**
     * List of role authorisation.
     *
     * @param role String USER or ADMIN, attribute of User object.
     * @return a list of role.
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
