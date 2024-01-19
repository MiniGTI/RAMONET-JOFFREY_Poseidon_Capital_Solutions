package com.nnk.springboot.integrationRepositoryTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointTests {
    
    @Autowired
    private CurvePointRepository curvePointRepository;
    @Autowired
    private TestEntityManager entityManager;
    private final CurvePoint curvePoint = new CurvePoint(10d, 30d);
    
    @Test
    public void curvePointSaveTest() {
        
        CurvePoint result = curvePointRepository.save(curvePoint);
        
        Assertions.assertEquals(curvePoint, result);
    }
    
    @Test
    public void curvePointUpdateTest() {
        entityManager.persist(curvePoint);
        curvePoint.setValue(40d);
        curvePointRepository.save(curvePoint);
        
        Assertions.assertEquals(curvePoint, entityManager.find(CurvePoint.class, curvePoint.getId()));
    }
    
    @Test
    public void curvePointFindByIdTest() {
        entityManager.persist(curvePoint);
        Optional<CurvePoint> result = curvePointRepository.findById(curvePoint.getId());
        
        Assertions.assertEquals(curvePoint.getId(), result.get()
                .getId());
    }
    
    @Test
    public void curvePointFindAllTest() {
        entityManager.persist(curvePoint);
        List<CurvePoint> result = curvePointRepository.findAll();
        
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    public void curvePointDeleteTest() {
        entityManager.persist(curvePoint);
        curvePointRepository.deleteById(curvePoint.getId());
        
        Assertions.assertNull(entityManager.find(CurvePoint.class, curvePoint.getId()));
    }
}
