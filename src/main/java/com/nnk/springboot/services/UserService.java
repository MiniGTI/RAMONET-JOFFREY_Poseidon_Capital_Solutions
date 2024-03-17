package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User getById(int id) {
        Optional<User> optUser = userRepository.findById(id);
        User user;
        
        if(optUser.isPresent()) {
            user = optUser.get();
        } else
            throw new UsernameNotFoundException("User id: " + id + " not found.");
        return user;
    }
    
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    public List<User> getAllByRole(String role) {
        return userRepository.finAllByRole(role);
    }
    
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    
    /**
     * Method to return the Username of the Authenticated user.
     * Use by Controllers class to display the user's name on the top of the page.
     * Verify if the Principal is an instance of OAuth2AuthenticatonToken, to get the name attribute if it is.
     * If the authentication is a basic and not an OAuth2 authentication, get the name of the principal.
     *
     * Implementation test of the new Switch pattern of JAVA 21.
     *
     * @param principal the user Authenticated
     * @return the username.
     */
    public String getUserName(Object principal) {

        switch(principal){
            case OAuth2AuthenticationToken authToken -> {
                Map<String, Object> userAttributes = (authToken.getPrincipal()).getAttributes();
                return userAttributes.get("name").toString();
            }
            case Principal auth -> {
                return auth.getName();
            }
            default -> throw new UsernameNotFoundException("User not found.");
        }
    }
    
    
    /**
     * Call the save method of the User repository to save a new User with the UserDto parsed.
     * Used by the /user/validate form in the add.html page.
     * Managed by the UserController.
     *
     * @param newUserDto the Dto object created by the add form in the user/add.html page.
     * @return call the save méthode of the User repository.
     */
    public User save(NewUserDto newUserDto) {
        log.debug("Informations parsed to save are: username: " + newUserDto.getUsername() + " fullname: " +
                newUserDto.getFullname() + " role: " + newUserDto.getRole());
        return userRepository.save(User.builder()
                .username(newUserDto.getUsername())
                .password(passwordEncoder.encode(newUserDto.getPassword()))
                .fullname(newUserDto.getFullname())
                .role(newUserDto.getRole())
                .build());
    }
    
    /**
     * Call the save method of the User repository to save changes on the UserDto parsed.
     * Used by the /user/update/{id} form in the update.html page.
     * Managed by the UserController.
     *
     * @param updateUserDto the UserDto created with the update form.
     *                      First, get the User to update with the id attribute of the UserDto parsed.
     *                      After that, check all attributes to verify if are empty. If not, set the User to update with the attributes of the UserDto parsed.
     *                      Before saving the new password, call passwordEncoder to encode it.
     * @return call the save method of the User repository with the User updated.
     */
    public User update(UpdateUserDto updateUserDto) {
        log.debug("Informations parsed to update are: username: " + updateUserDto.getUsername() + " fullname: " +
                updateUserDto.getFullname() + " role: " + updateUserDto.getRole());
        
        User userToUpdate = getById(updateUserDto.getId());
        
        if(!updateUserDto.getUsername()
                .isEmpty()) {
            userToUpdate.setUsername(updateUserDto.getUsername());
        }
        if(!updateUserDto.getPassword()
                .isEmpty()) {
            userToUpdate.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }
        if(!updateUserDto.getFullname()
                .isEmpty()) {
            userToUpdate.setFullname(updateUserDto.getFullname());
        }
        if(updateUserDto.getRole() != null) {
            userToUpdate.setRole(updateUserDto.getRole());
        }
        log.debug(
                "The userToUpdate attributes: id:" + userToUpdate.getId() + " username: " + userToUpdate.getUsername() +
                        " fullname: " + userToUpdate.getFullname() + " role: " + userToUpdate.getRole());
        
        return userRepository.save(userToUpdate);
    }
    
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
