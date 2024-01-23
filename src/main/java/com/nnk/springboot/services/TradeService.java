package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for the Trade object.
 * Perform all business processing between controllers and the TradeRepository.
 */
@Service
public class TradeService {
    /**
     * Call of slf4j class.
     */
    private final static Logger logger = LoggerFactory.getLogger(TradeService.class);
    
    /**
     * Call the TradeRepository to perform CRUDs request to the database.
     */
    private final TradeRepository tradeRepository;
    
    /**
     * The call constructor.
     *
     * @param tradeRepository to perform CRUDs request to the database.
     */
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }
    
    /**
     * Call the save method of the Trade repository.
     *
     * @param trade the Trade to save.
     * @return call the save method of the Trade repository with the Trade parsed.
     */
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }
    
    /**
     * Call the save method of the Trade repository to save a new Trade with the TradeDto parsed.
     * Used by the /trade/validate form in the add.html page.
     * Managed by the TradeController.
     *
     * @param tradeDto the Dto object created by the add form in the trade/add.html page.
     * @return call the save méthode of the Trade repository.
     */
    public Trade save(TradeDto tradeDto) {
        logger.debug(
                "Informations parsed to save are: account: " + tradeDto.getAccount() + " type: " + tradeDto.getType() +
                        " buyQuantity: " + tradeDto.getBuyQuantity());
        
        return tradeRepository.save(Trade.builder()
                .account(tradeDto.getAccount())
                .type(tradeDto.getType())
                .buyQuantity(tradeDto.getBuyQuantity())
                .build());
    }
    
    /**
     * Call the findById method of the Trade repository.
     * <p>
     * Get the Optional Trade return by the repository.
     * Throws an Exception if the Trade Repository return an empty Optional.
     *
     * @param id id of the Trade object parsed.
     * @return The Trade found.
     */
    public Trade getById(Integer id) {
        Optional<Trade> optTrade = tradeRepository.findById(id);
        Trade trade;
        
        if(optTrade.isPresent()) {
            trade = optTrade.get();
        } else {
            throw new RuntimeException("Trade id: " + id + " not found.");
        }
        return trade;
    }
    
    /**
     * Call the findAll method of the Trade repository.
     *
     * @return A List of all Trade object present in the Trade table.
     */
    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }
    
    /**
     * Call the save method of the Trade repository to save changes on the TradeDto parsed.
     * Used by the /trade/update/{id} form in the update.html page.
     * Managed by the TradeController.
     *
     * @param tradeDto the TradeDto created with the update form.
     *                 First, get the Trade to update with the id attribute of the TradeDto parsed.
     *                 After that, check all attributes to verify if are empty. If not, set the Trade to update with the attributes of the TradeDto parsed.
     * @return call the save method of the Trade repository with the Trade updated.
     */
    public Trade update(TradeDto tradeDto) {
        logger.debug("Informations parsed to update are: account: " + tradeDto.getAccount() + " type: " +
                tradeDto.getType() + " buyQuantity: " + tradeDto.getBuyQuantity());
        
        Trade tradeUpdated = getById(tradeDto.getId());
        
        if(!tradeDto.getAccount()
                .isEmpty()) {
            tradeUpdated.setAccount(tradeDto.getAccount());
        }
        if(!tradeDto.getType()
                .isEmpty()) {
            tradeUpdated.setType(tradeDto.getType());
        }
        if(tradeDto.getBuyQuantity() != null) {
            tradeUpdated.setBuyQuantity(tradeDto.getBuyQuantity());
        }
        logger.debug("The tradeUpdated attributes: id: " + tradeUpdated.getId() + " account: " + tradeDto.getAccount() +
                " type: " + tradeDto.getType() + " buyQuantity: " + tradeDto.getBuyQuantity());
        
        return tradeRepository.save(tradeUpdated);
    }
    
    /**
     * Call the deleteById method of the Trade repository.
     * Used to delete the Trade in the /trade/list.html.
     */
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}

