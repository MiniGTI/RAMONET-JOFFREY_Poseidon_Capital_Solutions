package com.nnk.springboot.integrationRepositoryTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeTests {
    
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private TestEntityManager entityManager;
    
    private final Trade trade = new Trade("Trade Account", "Type");
    
    
    @Test
    public void tradeSaveTest() {
        Trade result = tradeRepository.save(trade);
        
        Assertions.assertEquals(trade, result);
    }
    
    @Test
    public void tradeUpdateTest() {
        entityManager.persist(trade);
        trade.setType("newType");
        tradeRepository.save(trade);
        
        Assertions.assertEquals(trade, entityManager.find(Trade.class, trade.getId()));
    }
    
    @Test
    public void tradeFindByIdTest() {
        entityManager.persist(trade);
        Optional<Trade> result = tradeRepository.findById(trade.getId());
        
        Assertions.assertEquals(trade.getId(), result.get()
                .getId());
    }
    
    @Test
    public void tradeFindAllTest() {
        entityManager.persist(trade);
        List<Trade> result = tradeRepository.findAll();
        
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    public void tradeDeleteByIdTest() {
        entityManager.persist(trade);
        tradeRepository.deleteById(trade.getId());
        
        Assertions.assertNull(entityManager.find(Trade.class, trade.getId()));
    }
}
