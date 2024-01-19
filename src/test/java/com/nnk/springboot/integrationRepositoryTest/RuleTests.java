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
    
    private final RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    
    @Test
    public void ruleSaveTest() {
        RuleName result = ruleNameRepository.save(rule);
        
        Assertions.assertEquals(rule, result);
    }
    
    @Test
    public void ruleUpdateTest() {
        entityManager.persist(rule);
        rule.setName("nex rule name");
        ruleNameRepository.save(rule);
        
        Assertions.assertEquals(rule, entityManager.find(RuleName.class, rule.getId()));
    }
    
    @Test
    public void ruleFindByIdTest() {
        entityManager.persist(rule);
        Optional<RuleName> result = ruleNameRepository.findById(rule.getId());
        
        Assertions.assertEquals(rule.getId(), result.get()
                .getId());
    }
    
    @Test
    public void ruleFinAllTest() {
        entityManager.persist(rule);
        List<RuleName> result = ruleNameRepository.findAll();
        
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    public void ruleDeleteByIdTest() {
        entityManager.persist(rule);
        ruleNameRepository.deleteById(rule.getId());
        
        Assertions.assertNull(entityManager.find(RuleName.class, rule.getId()));
    }
 
}
