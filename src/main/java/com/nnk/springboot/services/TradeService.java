package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradeService {

    private final TradeRepository tradeRepository;
    
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }
    
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
    
    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }
    
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
        log.debug(
                "Informations parsed to save are: account: " + tradeDto.getAccount() + " type: " + tradeDto.getType() +
                        " buyQuantity: " + tradeDto.getBuyQuantity());
        
        return tradeRepository.save(Trade.builder()
                .account(tradeDto.getAccount())
                .type(tradeDto.getType())
                .buyQuantity(tradeDto.getBuyQuantity())
                .build());
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
        log.debug("Informations parsed to update are: account: " + tradeDto.getAccount() + " type: " +
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
        log.debug(
                "The tradeUpdated attributes: id: " + tradeUpdated.getId() + " account: " + tradeUpdated.getAccount() +
                        " type: " + tradeUpdated.getType() + " buyQuantity: " + tradeUpdated.getBuyQuantity());
        
        return tradeRepository.save(tradeUpdated);
    }
    
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}

