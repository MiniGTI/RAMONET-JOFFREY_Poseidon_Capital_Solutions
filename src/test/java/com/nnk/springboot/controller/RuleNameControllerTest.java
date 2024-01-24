package com.nnk.springboot.controller;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
public class RuleNameControllerTest {
    
    @MockBean
    private RuleNameService ruleNameService;
    
    @Autowired
    private MockMvc mvc;
    
    private final RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
    private final List<RuleName> ruleNames = new ArrayList<>(List.of(ruleName, new RuleName()));
    private final RuleNameDto ruleNameDto =
            new RuleNameDto(1, "newName", "newDescritpion", "newJson", "newTemplate", "newSqlStr", "newSqlPart");
    
    @Test
    @WithMockUser
    void shouldReturnRuleNameListPageTest() throws Exception {
        when(ruleNameService.getAll()).thenReturn(ruleNames);
        
        mvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ruleNames", hasSize(2)));
    }
    
    @Test
    @WithMockUser
    void shouldReturnRuleNameAddPageTest() throws Exception {
        mvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    void shouldReturnRuleNameValidateFormTest() throws Exception {
        RequestBuilder request = post("/ruleName/validate").param("name", ruleNameDto.getName())
                .param("description", ruleNameDto.getDescription())
                .param("json", ruleNameDto.getJson())
                .param("template", ruleNameDto.getTemplate())
                .param("sqlStr", ruleNameDto.getSqlStr())
                .param("sqlPart", ruleNameDto.getSqlPart())
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
    
    @Test
    @WithMockUser
    void shouldReturnRuleNameUpdatePage() throws Exception {
        when(ruleNameService.getById(1)).thenReturn(ruleName);
        
        mvc.perform(get("/ruleName/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ruleName", hasProperty("name", is("name"))));
    }
    
    @Test
    @WithMockUser
    void shouldUpdateRuleNameWithRuleNameDtoTest() throws Exception {
        RequestBuilder request = post("/ruleName/update/{id}", 1).param("template", "newTemplate")
                .with(csrf());
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(redirectedUrl("/ruleName/list"));
    
    }
    
    @Test
    @WithMockUser
    void shouldDeleteRuleName() throws Exception{
        doNothing().when(ruleNameService).deleteById(1);
        mvc.perform(get("/ruleName/delete/{id}", 1)).andExpect(redirectedUrl("/ruleName/list"));
        
    }
}
