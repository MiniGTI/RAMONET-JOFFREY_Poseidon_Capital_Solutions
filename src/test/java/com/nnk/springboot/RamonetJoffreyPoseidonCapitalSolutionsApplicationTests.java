package com.nnk.springboot;

import com.nnk.springboot.configuration.Data;
import com.nnk.springboot.controllers.ErrorController;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RamonetJoffreyPoseidonCapitalSolutionsApplicationTests {
    
    @Autowired
    private ErrorController errorController;
    @MockBean
    private UserService userService;
    @MockBean
    private Data data;

    @Test
    void contextLoads() {
        
        assertThat(errorController).isNotNull();
    }
}
