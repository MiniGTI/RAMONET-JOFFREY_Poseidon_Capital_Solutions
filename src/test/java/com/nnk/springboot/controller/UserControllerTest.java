package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDto;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private BCryptPasswordEncoder passwordEncoderMock;
    
    @Autowired
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
    private final NewUserDto userDto = NewUserDto.builder()
            .username("username")
            .password("Azerty123+")
            .fullname("fullname")
            .role("USER")
            .build();
    
    private final User user = new User(1, "username", passwordEncoder.encode("Azerty123+"), "fullname", "USER");
    
    
    @Test
    @WithMockUser(
            roles = "ADMIN"
    )
    void shouldReturnUserListTest() throws Exception {
        List<User> users = new ArrayList<>(List.of(new User(), new User()));
        
        when(userService.getAll()).thenReturn(users);
        
        mvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", hasSize(2)));
    }
    
    @Test
    @WithMockUser(
            roles = "USER"
    )
    void shouldReturn403ErrorWhenUserTryToGetUserListTest() throws Exception {
        mvc.perform(get("/user/list"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAddPageTest() throws Exception{
        mvc.perform(get("/user/add")).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUserValidatePageTest() throws Exception{
        RequestBuilder request = post("/user/validate").param("fullname", userDto.getUsername()).param("username", userDto.getUsername()).param("password", userDto.getPassword()).param("role", userDto.getRole()).with(csrf());
 mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(redirectedUrl("/user/list"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUserUpdatePageTest() throws Exception{
        when(userService.getById(1)).thenReturn(user);
        mvc.perform(get("/user/update/{id}", 1)).andExpect(status().isOk()).andExpect(model().attribute("user", is(user)));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldeUpdateUserWithUserDtoTest() throws Exception{
        userDto.setPassword("Qwerty123+");
        userDto.setRole("USER");
        RequestBuilder request = post("/user/update/{id}",1).param("username", userDto.getUsername())
                .param("password", userDto.getPassword()).param("fullname", userDto.getFullname()).param("role", userDto.getRole()).with(csrf());
        
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(redirectedUrl("/user/list"));
    }
    
    @Test
    @WithMockUser(roles ="ADMIN")
    void shouldDeleteUserTest() throws Exception{
        when(userService.getById(1)).thenReturn(user);
        doNothing().when(userService).deleteById(1);
        mvc.perform(get("/user/delete/{1}", 1)).andExpect(redirectedUrl("/user/list"));
    }
    
}
