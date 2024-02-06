package com.nnk.springboot.securityTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test class use the default account of the com.nnk.springboot.configuration.Data
 */
@AutoConfigureMockMvc
@SpringBootTest
public class SecurityTest {
  
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void shouldReturnLoginFormPageTest() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
    
    @Test
    void formLoginShouldRedirectAdminTest() throws Exception{
        mvc.perform(formLogin().user("sudo").password("toDelete1+")).andExpect(redirectedUrl("/"));
    }

    @Test
    void formLoginShouldRedirectUserTest() throws Exception{
        mvc.perform(formLogin().user("user").password("toDelete1+")).andExpect(redirectedUrl("/bidList/list"));
    }
}
