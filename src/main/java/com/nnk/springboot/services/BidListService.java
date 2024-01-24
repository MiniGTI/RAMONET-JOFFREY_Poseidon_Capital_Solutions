package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for the BidList object.
 * Perform all business processing between controllers and the BidListRepository.
 */
@Service
public class BidListService {
    
    /**
     * Call of slf4j class.
     */
    private final static Logger logger = LoggerFactory.getLogger(RatingService.class);
    /**
     * Call the BidListRepository to perform CRUDs request to the database.
     */
    private final BidListRepository bidListRepository;
    
    /**
     * The call constructor.
     *
     * @param bidListRepository to perform CRUDs request to the database.
     */
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }
    
    /**
     * Call the findById method of the BidList repository.
     * <p>
     * Get the Optional BidList return by the repository.
     * Throws an Exception if the BidList Repository return an empty Optional.
     *
     * @param id id of the BidList object parsed.
     * @return The BidList found.
     */
    public BidList getById(Integer id) {
        Optional<BidList> optBidList = bidListRepository.findById(id);
        BidList bidList;
        if(optBidList.isPresent()) {
            bidList = optBidList.get();
        } else {
            throw new RuntimeException("BidList id: " + id + " not found.");
        }
        return bidList;
    }
    
    /**
     * Call the findAll method of the BidList repository.
     *
     * @return A List of all BidList object present in the BidList table.
     */
    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }
    
    /**
     * Call the save method of the BidList repository.
     *
     * @param bidList the BidList to save.
     * @return call the save method of the BidList repository with the BidList parsed.
     */
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }
    
    /**
     * Call the save method of the BidList repository to save a new BidList with the BidListDto parsed.
     * Used by the /bidList/validate form in the add.html page.
     * Managed by the BidListController.
     *
     * @param bidListDto the Dto object created by the add form in the bidList/add.html page.
     * @return call the save méthode of the BidList repository.
     */
    public BidList save(BidListDto bidListDto) {
        logger.debug("Informations parsed to save are: account: " + bidListDto.getAccount() + " type: " +
                bidListDto.getType() + " buyQuantity: " + bidListDto.getBidQuantity());
        
        return bidListRepository.save(BidList.builder()
                .account(bidListDto.getAccount())
                .type(bidListDto.getType())
                .bidQuantity(bidListDto.getBidQuantity())
                .build());
    }
    
    /**
     * Call the save method of the BidList repository to save changes on the BidListDto parsed.
     * Used by the /bidList/update/{id} form in the update.html page.
     * Managed by the BidListController.
     *
     * @param bidListDto the BidListDto created with the update form.
     *                   First, get the BidList to update with the id attribute of the BidListDto parsed.
     *                   After that, check all attributes to verify if are empty. If not, set the BidList to update with the attributes of the BidListDto parsed.
     * @return call the save method of the BidList repository with the BidList updated.
     */
    public BidList update(BidListDto bidListDto) {
        logger.debug("Informations parsed to update are: account: " + bidListDto.getAccount() + " type: " +
                bidListDto.getType() + " buyQuantity: " + bidListDto.getBidQuantity());
        
        BidList bidListToUpdate = getById(bidListDto.getId());
        
        if(!bidListDto.getAccount()
                .isEmpty()) {
            bidListToUpdate.setAccount(bidListDto.getAccount());
        }
        if(!bidListDto.getType()
                .isEmpty()) {
            bidListToUpdate.setType(bidListDto.getType());
        }
        if(bidListDto.getBidQuantity() != null) {
            bidListToUpdate.setBidQuantity(bidListDto.getBidQuantity());
        }
        
        logger.debug("The tradeUpdated attributes: id: " + bidListToUpdate.getId() + " account: " +
                bidListToUpdate.getAccount() + " type: " + bidListToUpdate.getType() + " buyQuantity: " +
                bidListToUpdate.getBidQuantity());
        return bidListRepository.save(bidListToUpdate);
    }
    
    /**
     * Call the deleteById method of the BidList repository.
     * Used to delete the BidList in the /bidList/list.html.
     */
    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
