package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

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
    
    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }
    
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
        log.debug("Informations parsed to save are: term: " + curvePointDto.getTerm() + " value: " +
                curvePointDto.getValue());
        return curvePointRepository.save(CurvePoint.builder()
                .term(curvePointDto.getTerm())
                .value(curvePointDto.getValue())
                .creationDate(LocalDate.now())
                .build());
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
        log.debug("Informations parsed to update are: term: " + curvePointDto.getTerm() + " value: " +
                curvePointDto.getValue());
        
        CurvePoint curvePointUpdated = getById(curvePointDto.getId());
        
        if(curvePointDto.getTerm() != null) {
            curvePointUpdated.setTerm(curvePointDto.getTerm());
        }
        if(curvePointDto.getValue() != null) {
            curvePointUpdated.setValue(curvePointDto.getValue());
        }
        
        log.debug("The curvePointUpdated attributes are: term: " + curvePointUpdated.getTerm() + " value: " +
                curvePointUpdated.getValue());
        
        return curvePointRepository.save(curvePointUpdated);
    }
    
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
