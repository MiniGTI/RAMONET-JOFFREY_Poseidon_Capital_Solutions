package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for the Rating object.
 * Perform all business processing between controllers and the RatingRepository.
 */
@Service
public class RatingService {
    
    /**
     * Call of slf4j class.
     */
    private final static Logger logger = LoggerFactory.getLogger(RatingService.class);
    
    /**
     * Call the RatingRepository to perform CRUDs request to the database.
     */
    private final RatingRepository ratingRepository;
    
    /**
     * The call constructor.
     *
     * @param ratingRepository to perform CRUDs request to the database.
     */
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    
    /**
     * Call the save method of the Rating repository.
     *
     * @param rating the rating to save.
     * @return call the save method of the Rating repository with the Rating parsed.
     */
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
    
    /**
     * Call the save method of the Rating repository to save a new Rating with the RatingDto parsed.
     * Used by the /rating/validate form in the add.html page.
     * Managed by the RatingController.
     *
     * @param ratingDto the Dto object created by the add form in the rating/add.html page.
     * @return call the save méthode of the Rating repository.
     */
    public Rating save(RatingDto ratingDto) {
        logger.debug("Informations parsed to save are: moodysRating: " + ratingDto.getMoodysRating() + " sandRating: " +
                ratingDto.getSandRating() + " fitchRating: " + ratingDto.getFitchRating() + " orderNumber: " +
                ratingDto.getOrderNumber());
        
        return ratingRepository.save(Rating.builder()
                .fitchRating(ratingDto.getFitchRating())
                .sandRating(ratingDto.getSandRating())
                .moodysRating(ratingDto.getMoodysRating())
                .orderNumber(ratingDto.getOrderNumber())
                .build());
    }
    
    /**
     * Call the save method of the Rating repository to save changes on the RatingDto parsed.
     * Used by the /rating/update/{id} form in the update.html page.
     * Managed by the RatingController.
     *
     * @param ratingDto the ratingDto created with the update form.
     *                  First, get the Rating to update with the id attribute of the RatingDto parsed.
     *                  After that, check all attributes to verify if are empty. If not, set the Rating to update with the attributes of the RatingDto parsed.
     * @return call the save method of the Rating repository with the Rating updated.
     */
    public Rating update(RatingDto ratingDto) {
        logger.debug(
                "Informations parsed to update are: moodysRating: " + ratingDto.getMoodysRating() + " sandRating: " +
                        ratingDto.getSandRating() + " fitchRating: " + ratingDto.getFitchRating() + " orderNumber: " +
                        ratingDto.getOrderNumber());
        
        Rating ratingUpdated = getById(ratingDto.getId());
        
        if(!ratingDto.getMoodysRating()
                .isEmpty()) {
            ratingUpdated.setMoodysRating(ratingDto.getMoodysRating());
        }
        if(!ratingDto.getSandRating()
                .isEmpty()) {
            ratingUpdated.setSandRating(ratingDto.getSandRating());
        }
        if(!ratingDto.getFitchRating()
                .isEmpty()) {
            ratingUpdated.setFitchRating(ratingDto.getFitchRating());
        }
        if(ratingDto.getOrderNumber() != null) {
            ratingUpdated.setOrderNumber(ratingDto.getOrderNumber());
        }
        logger.debug("The ratingUpdated attributes: id: " + ratingUpdated.getId() + " moodysRating: " +
                ratingDto.getMoodysRating() + " sandRating: " + ratingDto.getSandRating() + "fitchRating: " +
                ratingDto.getFitchRating() + " orderNumber: " + ratingDto.getOrderNumber());
        return ratingRepository.save(ratingUpdated);
    }
    
    /**
     * Call the findById method of the Rating repository.
     * <p>
     * Get the Optional Rating return by the repository.
     * Throws an Exception if the Rating Repository return an empty Optional.
     *
     * @param id id of the Rating object parsed.
     * @return The Rating found.
     */
    public Rating getById(int id) {
        Rating rating;
        Optional<Rating> optRating = ratingRepository.findById(id);
        
        if(optRating.isPresent()) {
            rating = optRating.get();
        } else {
            throw new RuntimeException("Rating id: " + id + " not found.");
        }
        return rating;
    }
    
    /**
     * Call the findAll method of the Rating repository.
     *
     * @return A List of all Rating object present in the rating table.
     */
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }
    
    /**
     * Call the deleteById method of the Rating repository.
     * Used to delete the Rating in the /rating/list.html.
     */
    public void deleteById(int id) {
        ratingRepository.deleteById(id);
    }
}
