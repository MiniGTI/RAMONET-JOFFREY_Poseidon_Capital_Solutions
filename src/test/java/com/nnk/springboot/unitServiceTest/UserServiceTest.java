package com.nnk.springboot.unitServiceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
    @MockBean
    private BCryptPasswordEncoder passwordEncoderMock;
    
    private final String password = passwordEncoder.encode("test");
    
    private final User user = new User(1, "username", password, "fullname", "USER");
    private final NewUserDto userDto = new NewUserDto();
    private final User updatedUser = new User();
    private final UpdateUserDto updateUserDto = new UpdateUserDto();
    
    @Test
    void shouldReturnUserByIdTest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        
        User result = userService.getById(1);
        
        assertEquals(user, result);
    }
    
    @Test
    void shouldReturnUserByUsernameTest() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        
        Optional<User> result = userService.getByUsername("username");
        
        assertEquals(user, result.get());
    }
    
    @Test
    void shouldReturnAllUserByGetAllTest() {
        User user2 = new User();
        List<User> users = new ArrayList<>(List.of(user, user2));
        
        when(userRepository.findAll()).thenReturn(users);
        
        List<User> result = userService.getAll();
        
        assertTrue(result.containsAll(List.of(user, user2)));
    }
    
    @Test
    void shouldSaveUserTest() {
        when(userRepository.save(user)).thenReturn(user);
        
        User result = userService.save(user);
        
        assertEquals(user, result);
    }
    
    @Test
    void shouldSaveUserWithUserDtoTest() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        User result = userService.save(userDto);
        
        assertEquals(user, result);
    }
    
    @Test
    void shouldUpdateUserWhenUsernameIsEmptyTest() {
        updateUserDto.setId(1);
        updateUserDto.setUsername("");
        updateUserDto.setPassword("testtest");
        updateUserDto.setFullname("newFullname");
        updateUserDto.setRole("ADMIN");
        updatedUser.setPassword(passwordEncoder.encode("testtest"));
        updatedUser.setFullname("newFullname");
        updatedUser.setRole("ADMIN");
        
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        
        User result = userService.update(updateUserDto);
        
        assertEquals(updatedUser, result);
    }
    
    @Test
    void shouldUpdateUserWhenPasswordIsEmptyTest() {
        updateUserDto.setId(1);
        updateUserDto.setUsername("newUsername");
        updateUserDto.setPassword("");
        updateUserDto.setFullname("newFullname");
        updateUserDto.setRole("ADMIN");
        updatedUser.setUsername("newUsername");
        updatedUser.setFullname("newFullname");
        updatedUser.setRole("ADMIN");
        
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        
        User result = userService.update(updateUserDto);
        
        assertEquals(updatedUser, result);
    }
    
    
    @Test
    void shouldUpdateUserWhenFullnameIsEmptyTest() {
        updateUserDto.setId(1);
        updateUserDto.setUsername("newUsername");
        updateUserDto.setPassword("testtest");
        updateUserDto.setFullname("");
        updateUserDto.setRole("ADMIN");
        updatedUser.setUsername("newUsername");
        updatedUser.setPassword(passwordEncoder.encode("testtest"));
        updatedUser.setRole("ADMIN");
        
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        
        User result = userService.update(updateUserDto);
        
        assertEquals(updatedUser, result);
    }
    
    @Test
    void shouldUpdateUserWhenRoleIsNullTest() {
        updateUserDto.setId(1);
        updateUserDto.setUsername("newUsername");
        updateUserDto.setPassword("testtest");
        updateUserDto.setFullname("newFullname");
        updatedUser.setUsername("newUsername");
        updatedUser.setPassword(passwordEncoder.encode("testtest"));
        updatedUser.setFullname("newFullname");
        
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        
        User result = userService.update(updateUserDto);
        
        assertEquals(updatedUser, result);
    }
    
    @Test
    void shouldDeleteUserTest() {
        doNothing().when(userRepository)
                .deleteById(user.getId());
        assertDoesNotThrow(() -> userService.deleteById(user.getId()));
    }
}
