package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for the RuleName object.
 * Perform all business processing between controllers and the RuleNameRepository.
 */
@Service
public class RuleNameService {
    /**
     * Call of slf4j class.
     */
    private final static Logger logger = LoggerFactory.getLogger(RuleNameService.class);
    
    /**
     * Call the RuleNameRepository to perform CRUDs request to the database.
     */
    private final RuleNameRepository ruleNameRepository;
    
    /**
     * The call constructor.
     *
     * @param ruleNameRepository to perform CRUDs request to the database.
     */
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }
    
    /**
     * Call the save method of the RuleName repository.
     *
     * @param ruleName the rating to save.
     * @return call the save method of the RuleName repository with the RuleName parsed.
     */
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }
    
    /**
     * Call the save method of the RuleName repository to save a new RuleName with the RuleNameDto parsed.
     * Used by the /ruleName/validate form in the add.html page.
     * Managed by the RuleNameController.
     *
     * @param ruleNameDto the Dto object created by the add form in the ruleName/add.html page.
     * @return call the save méthode of the RuleName repository.
     */
    public RuleName save(RuleNameDto ruleNameDto) {
        logger.debug("Informations parsed to save are: name: " + ruleNameDto.getName() + " description: " +
                ruleNameDto.getDescription() + " json: " + ruleNameDto.getJson() + " template: " +
                ruleNameDto.getTemplate() + " sqlStr: " + ruleNameDto.getSqlStr() + " sqlPart: " +
                ruleNameDto.getSqlPart());
        
        return ruleNameRepository.save(RuleName.builder()
                .name(ruleNameDto.getName())
                .description(ruleNameDto.getDescription())
                .json(ruleNameDto.getJson())
                .template(ruleNameDto.getTemplate())
                .sqlStr(ruleNameDto.getSqlStr())
                .sqlPart(ruleNameDto.getSqlPart())
                .build());
    }
    
    /**
     * Call the findById method of the RuleName repository.
     * <p>
     * Get the Optional RuleName return by the repository.
     * Throws an Exception if the RuleName Repository return an empty Optional.
     *
     * @param id id of the RuleName object parsed.
     * @return The RuleName found.
     */
    public RuleName getById(Integer id) {
        Optional<RuleName> optRuleName = ruleNameRepository.findById(id);
        RuleName ruleName;
        
        if(optRuleName.isPresent()) {
            ruleName = optRuleName.get();
        } else {
            throw new RuntimeException("RuleName id: " + id + " not found.");
        }
        return ruleName;
    }
    
    /**
     * Call the findAll method of the RuleName repository.
     *
     * @return A List of all RuleName object present in the RuleName table.
     */
    public List<RuleName> getAll() {
        return ruleNameRepository.findAll();
    }
    
    /**
     * Call the save method of the RuleName repository to save changes on the RuleNameDto parsed.
     * Used by the /ruleName/update/{id} form in the update.html page.
     * Managed by the RuleNameController.
     *
     * @param ruleNameDto the RuleNameDto created with the update form.
     *                    First, get the RuleName to update with the id attribute of the RuleNameDto parsed.
     *                    After that, check all attributes to verify if are empty. If not, set the RuleName to update with the attributes of the RuleNameDto parsed.
     * @return call the save method of the RuleName repository with the RuleName updated.
     */
    public RuleName update(RuleNameDto ruleNameDto) {
        logger.debug("Informations parsed to update are: name: " + ruleNameDto.getName() + " description: " +
                ruleNameDto.getDescription() + " json: " + ruleNameDto.getJson() + " template: " +
                ruleNameDto.getTemplate() + " sqlStr: " + ruleNameDto.getSqlStr() + " sqlPart: " +
                ruleNameDto.getSqlPart());
        
        RuleName ruleNameUpdated = getById(ruleNameDto.getId());
        
        if(!ruleNameDto.getName()
                .isEmpty()) {
            ruleNameUpdated.setName(ruleNameDto.getName());
        }
        if(!ruleNameDto.getDescription()
                .isEmpty()) {
            ruleNameUpdated.setDescription(ruleNameDto.getDescription());
        }
        if(!ruleNameDto.getJson()
                .isEmpty()) {
            ruleNameUpdated.setJson(ruleNameDto.getJson());
        }
        if(!ruleNameDto.getTemplate()
                .isEmpty()) {
            ruleNameUpdated.setTemplate(ruleNameDto.getTemplate());
        }
        if(!ruleNameDto.getSqlStr()
                .isEmpty()) {
            ruleNameUpdated.setSqlStr(ruleNameDto.getSqlStr());
        }
        if(!ruleNameDto.getSqlPart()
                .isEmpty()) {
            ruleNameUpdated.setSqlPart(ruleNameDto.getSqlPart());
        }
        logger.debug(
                "The ruleNameUpdated attributes: id: " + ruleNameUpdated.getId() + " name: " + ruleNameDto.getName() +
                        " description: " + ruleNameDto.getDescription() + " json: " + ruleNameDto.getJson() +
                        " template: " + ruleNameDto.getTemplate() + " sqlStr: " + ruleNameDto.getSqlStr() +
                        " sqlPart: " + ruleNameDto.getSqlPart());
        
        return ruleNameRepository.save(ruleNameUpdated);
    }
    
    /**
     * Call the deleteById method of the RuleName repository.
     * Used to delete the RuleName in the /ruleName/list.html.
     */
    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
