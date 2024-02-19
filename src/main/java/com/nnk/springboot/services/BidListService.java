package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class BidListService {

    private final BidListRepository bidListRepository;

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
    
    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }
    
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
        log.debug("Informations parsed to save are: account: " + bidListDto.getAccount() + " type: " +
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
        log.debug("Informations parsed to update are: account: " + bidListDto.getAccount() + " type: " +
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
        
        log.debug("The tradeUpdated attributes: id: " + bidListToUpdate.getId() + " account: " +
                bidListToUpdate.getAccount() + " type: " + bidListToUpdate.getType() + " buyQuantity: " +
                bidListToUpdate.getBidQuantity());
        return bidListRepository.save(bidListToUpdate);
    }
    
    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
