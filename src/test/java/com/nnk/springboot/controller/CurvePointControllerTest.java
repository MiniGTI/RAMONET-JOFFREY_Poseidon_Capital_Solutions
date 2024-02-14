package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private CurvePointService curvePointService;
    
    @MockBean
    private UserService userService;
    
    private final CurvePoint curvePoint = new CurvePoint(1, 10, LocalDate.now(), 10d, 35.0, LocalDate.now());
    
    private final List<CurvePoint> curvePoints = new ArrayList<>(List.of(curvePoint, new CurvePoint()));
    
    private final CurvePointDto curvePointDto = CurvePointDto.builder()
            .term(10d)
            .value(35d)
            .build();

    @Test
    @WithMockUser
    void shouldReturnCurvePointListPageTest() throws Exception {
        when(curvePointService.getAll()).thenReturn(curvePoints);
        when(userService.getUserName(any())).thenReturn("fullname");
        
        mvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fullname",  equalTo("fullname")))
                .andExpect(model().attribute("curvePoints", hasSize(2)));
    }
    
    @Test
    @WithMockUser
    void shouldRetunAddPageTest() throws Exception {
        mvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    void shouldReturnCurvePointValidateFormTest() throws Exception {
        RequestBuilder request = post("/curvePoint/validate").param("term", String.valueOf(curvePointDto.getTerm()))
                .param("value", String.valueOf(curvePointDto.getValue()))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
    
    @Test
    @WithMockUser
    void shouldReturnUpdatePageTest() throws Exception {
        when(curvePointService.getById(curvePoint.getId())).thenReturn(curvePoint);
        
        mvc.perform(get("/curvePoint/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("curvePoint", hasProperty("term", is(10d))));
    }
    
    @Test
    @WithMockUser
    void shouldUpdateCurvePointWithCurvePointDtoTest() throws Exception {
        curvePoint.setValue(35d);
        when(curvePointService.update(curvePointDto)).thenReturn(curvePoint);
        
        RequestBuilder request = post("/curvePoint/update/{id}", 1).param("term", String.valueOf(35d))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
    
    @Test
    @WithMockUser
    void shouldDeleteCurvePointTest() throws Exception {
        doNothing().when(curvePointService)
                .deleteById(1);
        mvc.perform(get("/curvePoint/delete/{id}", 1))
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
