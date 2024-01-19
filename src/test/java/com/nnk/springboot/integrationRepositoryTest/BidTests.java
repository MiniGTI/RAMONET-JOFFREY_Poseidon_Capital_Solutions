package com.nnk.springboot.integrationRepositoryTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidTests {
    
    @Autowired
    private BidListRepository bidListRepository;
    @Autowired
    private TestEntityManager entityManager;
    
    private final BidList bid = new BidList("Account Test", "Type Test", 10d);
    
    @Test
    public void bidListSaveTest() {
        BidList result = bidListRepository.save(bid);
        
        Assertions.assertEquals(bid, result);
    }
    
    @Test
    public void bidListUpdateTest() {
        entityManager.persist(bid);
        bid.setBidQuantity(20d);
        bidListRepository.save(bid);
        
        Assertions.assertEquals(bid, entityManager.find(BidList.class, bid.getId()));
    }
    
    @Test
    public void bidListFindByIdTest() {
        entityManager.persist(bid);
        
        Optional<BidList> result = bidListRepository.findById(bid.getId());
        
        Assertions.assertEquals(bid.getId(), result.get()
                .getId());
    }
    
    @Test
    public void bidListFindAllTest() {
        entityManager.persist(bid);
        
        List<BidList> result = bidListRepository.findAll();
        
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    public void bidListDeleteTest() {
        entityManager.persist(bid);
        
        bidListRepository.deleteById(bid.getId());
        
        Assertions.assertNull(entityManager.find(BidList.class, bid.getId()));
    }
}
