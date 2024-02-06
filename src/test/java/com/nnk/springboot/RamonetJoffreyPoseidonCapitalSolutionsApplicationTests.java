package com.nnk.springboot;

import com.nnk.springboot.controllers.ErrorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RamonetJoffreyPoseidonCapitalSolutionsApplicationTests {
    
    @Autowired
    private ErrorController errorController;
    @Test
    void contextLoads() {
        
        assertThat(errorController).isNotNull();
    }
}
