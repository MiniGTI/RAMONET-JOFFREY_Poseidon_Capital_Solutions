package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Class to input a default admin and user account.
 * If no account with role = ADMIN and USER present in the table, insert new user(s).
 */

@AllArgsConstructor
@Component
public class Data {
    
    private final UserService userService;

    /**
     * Call the getAllByRole method with ADMIN parameter of the userService.
     * If return an empty list, call the save method of the userService to persist the default admin account.
     *
     * @param event
     */
    @EventListener
    public void adminAccount(ApplicationReadyEvent event) {
        List<User> allAdmin = userService.getAllByRole("ADMIN");
        if(allAdmin.isEmpty()) {
            userService.save(User.builder()
                    .id(1)
                    .username("sudo")
                    .fullname("sudo")
                    .password("toDelete1+")
                    .role("ADMIN")
                    .build());
        }
    }
    
    /**
     * Call the getAllByRole method with USER parameter of the userService.
     * If return an empty list, call the save method of the userService to persist the default user account.
     *
     * @param event
     */
    @EventListener
    public void userAccount(ApplicationReadyEvent event) {
        List<User> allUser = userService.getAllByRole("USER");
        if(allUser.isEmpty()) {
            userService.save(User.builder()
                    .id(2)
                    .username("user")
                    .fullname("user")
                    .password("toDelete1+")
                    .role("USER")
                    .build());
        }
    }
}
