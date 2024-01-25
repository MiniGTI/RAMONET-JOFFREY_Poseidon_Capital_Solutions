package com.nnk.springboot.integrationRepositoryTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BidTests {
    
    @Autowired
    private BidListRepository bidListRepository;
    @Autowired
    private TestEntityManager entityManager;
    
   private final BidList bidList = BidList.builder()
            .account("Account Test")
            .type("Type Test")
            .bidQuantity(10d)
            .build();
    @Test
    public void bidListSaveTest() {
        BidList result = bidListRepository.save(bidList);
        
        assertEquals(bidList, result);
    }
    
    @Test
    public void bidListUpdateTest() {
        entityManager.persist(bidList);
        bidList.setBidQuantity(20d);
        bidListRepository.save(bidList);
        
        assertEquals(bidList, entityManager.find(BidList.class, bidList.getId()));
    }
    
    @Test
    public void bidListFindByIdTest() {
        entityManager.persist(bidList);
        
        Optional<BidList> result = bidListRepository.findById(bidList.getId());
        
        assertEquals(bidList.getId(), result.get()
                .getId());
    }
    
    @Test
    public void bidListFindAllTest() {
        entityManager.persist(bidList);
        
        List<BidList> result = bidListRepository.findAll();
        
        assertFalse(result.isEmpty());
    }
    
    @Test
    public void bidListDeleteTest() {
        entityManager.persist(bidList);
        
        bidListRepository.deleteById(bidList.getId());
        
        assertNull(entityManager.find(BidList.class, bidList.getId()));
    }
}
