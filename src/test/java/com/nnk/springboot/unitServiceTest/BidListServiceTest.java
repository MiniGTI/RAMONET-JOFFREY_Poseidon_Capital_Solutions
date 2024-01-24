package com.nnk.springboot.unitServiceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BidListServiceTest {
    
    @Autowired
    private BidListService bidListService;
    
    @MockBean
    private BidListRepository bidListRepository;
    
    private final BidList bidList = BidList.builder()
            .id(1)
            .account("account")
            .type("type")
            .bidQuantity(20.5)
            .build();
    private final BidListDto bidListDto = BidListDto.builder()
            .account("account")
            .type("type")
            .bidQuantity(20.5)
            .build();
    private final List<BidList> bidLists = new ArrayList<>(List.of(bidList, new BidList()));
    
    @Test
    void shouldGetByIdTest() {
        when(bidListRepository.findById(bidList.getId())).thenReturn(Optional.of(bidList));
        
        BidList result = bidListService.getById(bidList.getId());
        
        assertEquals(bidList.getId(), result.getId());
    }
    
    @Test
    void shouldReturnExceptionWhenGetByIdNotFoundBidList() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());
        
        RuntimeException runtimeException =
                Assertions.assertThrows(RuntimeException.class, () -> bidListService.getById(1));
        
        assertTrue("BidList id: 1 not found.".contains(runtimeException.getMessage()));
    }
    
    @Test
    void shouldGetAllBidListTest() {
        when(bidListRepository.findAll()).thenReturn(bidLists);
        
        List<BidList> result = bidListService.getAll();
        
        assertEquals(bidLists.size(), result.size());
    }
    
    @Test
    void saveBidListTest() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
        
        BidList result = bidListService.save(bidList);
        
        assertEquals(bidList.getId(), result.getId());
    }
    
    @Test
    void saveBidListWithBidListDtoTest() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
        
        BidList result = bidListService.save(bidListDto);
        
        assertEquals(bidList.getAccount(), result.getAccount());
    }
    
    @Test
    void updateBidListWithBidListDtoTest() {
        bidListDto.setType("new Type");
        bidListDto.setAccount("new Account");
        bidListDto.setBidQuantity(58.6);
        
        when(bidListRepository.findById(bidListDto.getId())).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(bidList)).thenReturn(bidList);
        
        BidList result = bidListService.update(bidListDto);
        
        assertEquals(bidList.getAccount(), result.getAccount());
        assertEquals(bidList.getType(), result.getType());
    }
    
    @Test
    void shouldDeleteByIdTest() {
        doNothing().when(bidListRepository)
                .deleteById(bidList.getId());
        
        bidListService.deleteById(bidList.getId());
        
        assertDoesNotThrow(() -> bidListService.deleteById(bidList.getId()));
    }
}
