package com.nnk.springboot.unitServiceTest;

import com.nnk.springboot.configuration.Data;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RuleNameServiceTest {
    
    @Autowired
    private RuleNameService ruleNameService;
    
    @MockBean
    private RuleNameRepository ruleNameRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private Data data;

    private final RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
    private final RuleNameDto ruleNameDto = RuleNameDto.builder()
            .name("Name")
            .description("Description")
            .json("json")
            .template("template")
            .sqlStr("sqlStr")
            .sqlPart("SqlPart")
            .build();
    private final List<RuleName> ruleNames = new ArrayList<>(List.of(ruleName, new RuleName()));
    
    @Test
    void shouldSaveTest() {
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        
        RuleName result = ruleNameService.save(ruleName);
        
        assertEquals(ruleName.getId(), result.getId());
    }
    
    @Test
    void shouldSaveWithRuleNameDtoTest() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);
        
        RuleName result = ruleNameService.save(ruleNameDto);
        
        assertEquals(ruleName.getId(),result.getId());
    }
    
    @Test
    void shouldGetByIdTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        
        RuleName result = ruleNameService.getById(1);
        
        assertEquals(ruleName.getId(), result.getId());
    }
    
    @Test
    void shouldReturnExceptionIfGetByIdNotFoundRuleNameTest(){
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        
        RuntimeException runtimeException= Assertions.assertThrows(RuntimeException.class, () -> ruleNameService.getById(1));
        
        assertTrue("RuleName id: 1 not found.".contains(runtimeException.getMessage()));
    }
    
    @Test
    void shouldGetAllTest() {
        when(ruleNameRepository.findAll()).thenReturn(ruleNames);
        
        List<RuleName> result = ruleNameService.getAll();
        
        assertEquals(ruleNames.size(), result.size());
    }
    
    @Test
    void shouldUpdateRuleNameTest() {
        RuleNameDto toUpdate = new RuleNameDto(1, "new Name", "new Description", "new json", "new template", "new sqlStr", "new SqlPart");
        
        when(ruleNameRepository.findById(toUpdate.getId())).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        
        RuleName result = ruleNameService.update(toUpdate);
        
        assertEquals(ruleName.getDescription(), result.getDescription());
        assertEquals(ruleName.getTemplate(), result.getTemplate());
    }
    
    @Test
    void shouldDeleteRuleNameTest(){
        doNothing().when(ruleNameRepository).deleteById(1);
        
        ruleNameService.deleteById(1);
        
        assertDoesNotThrow(() -> ruleNameService.deleteById(1));
    }
}
