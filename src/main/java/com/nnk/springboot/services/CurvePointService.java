package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for the CurvePoint object.
 * Perform all business processing between controllers and the CurvePointRepository.
 */
@Service
public class CurvePointService {
    
    
    /**
     * Call of slf4j class.
     */
    private final static Logger logger = LoggerFactory.getLogger(RatingService.class);
    /**
     * Call the CurvePointRepository to perform CRUDs request to the database.
     */
    private final CurvePointRepository curvePointRepository;
    
    /**
     * The call constructor.
     *
     * @param curvePointRepository to perform CRUDs request to the database.
     */
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }
    
    /**
     * Call the save method of the CurvePoint repository.
     *
     * @param curvePoint the CurvePoint to save.
     * @return call the save method of the CurvePoint repository with the CurvePoint parsed.
     */
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }
    
    /**
     * Call the save method of the CurvePoint repository to save a new CurvePoint with the CurvePointDto parsed.
     * Used by the /curvePoint/validate form in the add.html page.
     * Managed by the CurveController.
     * <p>
     * The new CurvePoint create take CurvePointDto's attributes and the creationDate is adding now.
     *
     * @param curvePointDto the Dto object created by the add form in the curve/add.html page.
     * @return call the save méthode of the CurvePoint repository.
     */
    public CurvePoint save(CurvePointDto curvePointDto) {
        logger.debug("Informations parsed to save are: term: " + curvePointDto.getTerm() + " value: " +
                curvePointDto.getValue());
        return curvePointRepository.save(CurvePoint.builder()
                .term(curvePointDto.getTerm())
                .value(curvePointDto.getValue())
                .creationDate(LocalDate.now())
                .build());
    }
    
    /**
     * Call the findById method of the CurvePoint repository.
     * <p>
     * Get the Optional CurvePoint return by the repository.
     * Throws an Exception if the CurvePoint Repository return an empty Optional.
     *
     * @param id id of the CurvePoint object parsed.
     * @return The CurvePoint found.
     */
    public CurvePoint getById(Integer id) {
        Optional<CurvePoint> optCurvePoint = curvePointRepository.findById(id);
        CurvePoint curvePoint;
        
        if(optCurvePoint.isPresent()) {
            curvePoint = optCurvePoint.get();
        } else {
            throw new RuntimeException("CurvePoint id: " + id + " not found.");
        }
        
        return curvePoint;
    }
    
    /**
     * Call the findAll method of the CurvePoint repository.
     *
     * @return A List of all CurvePoint object present in the CurvePoint table.
     */
    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }
    
    /**
     * Call the save method of the CurvePoint repository to save changes on the CurvePointDto parsed.
     * Used by the /curve/update/{id} form in the update.html page.
     * Managed by the CurveController.
     *
     * @param curvePointDto the curvePointDto created with the update form.
     *                      First, get the CurvePoint to update with the id attribute of the CurvePointDto parsed.
     *                      After that, check all attributes to verify if are empty. If not, set the CurvePoint to update with the attributes of the CurvePointDto parsed.
     * @return call the save method of the CurvePoint repository with the CurvePoint updated.
     */
    public CurvePoint update(CurvePointDto curvePointDto) {
        logger.debug("Informations parsed to update are: term: " + curvePointDto.getTerm() + " value: " +
                curvePointDto.getValue());
        
        CurvePoint curvePointUpdated = getById(curvePointDto.getId());
        
        if(curvePointDto.getTerm() != null) {
            curvePointUpdated.setTerm(curvePointDto.getTerm());
        }
        if(curvePointDto.getValue() != null) {
            curvePointUpdated.setValue(curvePointDto.getValue());
        }
        
        logger.debug("The curvePointUpdated attributes are: term: " + curvePointUpdated.getTerm() + " value: " +
                curvePointUpdated.getValue());
        
        return curvePointRepository.save(curvePointUpdated);
    }
    
    /**
     * Call the deleteById method of the CurvePoint repository.
     * Used to delete the CurvePoint in the /curve/list.html.
     */
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
