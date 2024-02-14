package com.nnk.springboot.integrationRepositoryTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingTests {
    
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private TestEntityManager entityManager;
    
    private final Rating rating = Rating.builder()
            .moodys("Moodys Rating")
            .sand("Sand Rating")
            .fitch("Fitch Rating")
            .order(10)
            .build();
    
    @Test
    public void ratingSaveTest() {
        Rating result = ratingRepository.save(rating);
        
        Assertions.assertEquals(rating, result);
    }
    
    @Test
    public void ratingUpdateTest() {
        entityManager.persist(rating);
        rating.setMoodys("new Moddys Rating");
        ratingRepository.save(rating);
        
        Assertions.assertEquals(rating, entityManager.find(Rating.class, rating.getId()));
    }
    
    @Test
    public void ratingFindByIdTest() {
        entityManager.persist(rating);
        Optional<Rating> result = ratingRepository.findById(rating.getId());
        
        Assertions.assertEquals(rating.getId(), result.get()
                .getId());
    }
    
    @Test
    public void ratingFindAllTest() {
        entityManager.persist(rating);
        List<Rating> result = ratingRepository.findAll();
        
        Assertions.assertFalse(result.isEmpty());
    }
    
    @Test
    public void ratingDeleteByIdTest() {
        entityManager.persist(rating);
        ratingRepository.deleteById(rating.getId());
        
        Assertions.assertNull(entityManager.find(Rating.class, rating.getId()));
    }
}
