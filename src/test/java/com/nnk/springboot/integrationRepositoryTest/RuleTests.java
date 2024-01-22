package com.nnk.springboot.integrationRepositoryTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RuleTests {
    
    @Autowired
    private RuleNameRepository ruleNameRepository;
    @Autowired
    private TestEntityManager entityManager;
    private final RuleName ruleName = RuleName.builder()
            .name("Rule Name")
            .description("Description")
            .json("json")
            .template("Template")
            .sqlStr("SQL")
            .sqlPart("SQL Part")
            .build();
    @Test
    public void ruleSaveTest() {
        RuleName result = ruleNameRepository.save(ruleName);
        
        Assertions.assertEquals(ruleName, result);
    }
    
    @Test
    public void ruleUpdateTest() {
        entityManager.persist(ruleName);
        ruleName.setName("nex rule name");
        ruleNameRepository.save(ruleName);
        
        Assertions.assertEquals(ruleName, entityManager.find(RuleName.class, ruleName.getId()));
    }
    
    @Test
    public void ruleFindByIdTest() {
        entityManager.persist(ruleName);
        Optional<RuleName> result = ruleNameRepository.findById(ruleName.getId());
        
        Assertions.assertEquals(ruleName.getId(), result.get()
                .getId());
    }
    
    @Test
    public void ruleFinAllTest() {
        entityManager.persist(ruleName);
        List<RuleName> result = ruleNameRepository.findAll();
        
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    public void ruleDeleteByIdTest() {
        entityManager.persist(ruleName);
        ruleNameRepository.deleteById(ruleName.getId());
        
        Assertions.assertNull(entityManager.find(RuleName.class, ruleName.getId()));
    }
 
}
