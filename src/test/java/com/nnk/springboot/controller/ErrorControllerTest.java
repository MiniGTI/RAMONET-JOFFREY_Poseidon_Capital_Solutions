package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    @WithMockUser
    void shouldReturnError403PageTest() throws Exception{
        when(userService.getUserName(any())).thenReturn("fullname");
        mvc.perform(get("/error403")).andExpect(status().isOk()).andExpect(model().attribute("fullname", equalTo("fullname")));
    }
}
