package com.nnk.springboot.unitServiceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.TradeService;
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
public class TradeServiceTest {
    
    @Autowired
    private TradeService tradeService;
    
    @MockBean
    private TradeRepository tradeRepository;
    
    private final Trade trade = Trade.builder()
            .id(1)
            .account("account")
            .type("type")
            .buyQuantity(20.5)
            .build();
    
    private final TradeDto tradeDto = TradeDto.builder()
            .id(1)
            .account("account")
            .type("type")
            .buyQuantity(20.5)
            .build();
    
    @Test
    void saveTest() {
        when(tradeRepository.save(trade)).thenReturn(trade);
        
        Trade result = tradeService.save(trade);
        
        assertEquals(trade.getId(), result.getId());
    }
    
    @Test
    void saveWithTradeDtoTest() {
        
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
        
        Trade tradeToSave = tradeService.save(tradeDto);
        
        assertEquals(trade.getId(), tradeToSave.getId());
    }
    
    @Test
    void findByIdShouldReturnTheExpectedTradeTest() {
        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));
        
        Trade result = tradeService.getById(trade.getId());
        
        assertEquals(trade.getId(), result.getId());
    }
    
    @Test
    void shouldReturnExceptionIfGetByIdNotFoundTradeTest() {
        Integer id = 1;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());
        
        RuntimeException runtimeException =
                Assertions.assertThrows(RuntimeException.class, () -> tradeService.getById(id));
        
        assertTrue("Trade id: 1 not found.".contains(runtimeException.getMessage()));
    }
    
    @Test
    void findAllShouldReturnAListOfAllTradeTest() {
        List<Trade> tradeList = new ArrayList<>(List.of(trade));
        
        when(tradeRepository.findAll()).thenReturn(tradeList);
        
        List<Trade> result = tradeService.getAll();
        
        assertFalse(result.isEmpty());
    }
    
    @Test
    void shouldUpdateTradeTest() {
        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));
        tradeDto.setAccount("new Account");
        tradeDto.setType("new Type");
        tradeDto.setBuyQuantity(55.1);
        trade.setAccount(tradeDto.getAccount());
        trade.setType(tradeDto.getType());
        trade.setBuyPrice(tradeDto.getBuyQuantity());
        when(tradeRepository.save(trade)).thenReturn(trade);
        
        Trade tradeUpdated = tradeService.update(tradeDto);
        
        assertEquals(trade, tradeUpdated);
    }
    
    @Test
    void shouldDeleteByIdTradeTest() {
        doNothing().when(tradeRepository)
                .deleteById(1);
        
        tradeService.deleteById(1);
        
        assertDoesNotThrow(() -> tradeService.deleteById(1));
    }
}
