package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private TradeService tradeService;
    @MockBean
    private UserService userService;
    
    private final Trade trade = Trade.builder()
            .account("account")
            .type("type")
            .buyQuantity(20.5)
            .build();
    private final TradeDto tradeDto = TradeDto.builder()
            .account("account")
            .type("type")
            .buyQuantity(20.5)
            .build();
    private final List<Trade> trades = new ArrayList<>(List.of(trade, new Trade()));
    
    @Test
    @WithMockUser
    void shouldReturnTradeListPageTest() throws Exception {
        when(tradeService.getAll()).thenReturn(trades);
        when(userService.getUserName(any())).thenReturn("fullname");
        mvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fullname", equalTo("fullname")))
                .andExpect(model().attribute("trades", hasSize(2)));
    }
    
    @Test
    @WithMockUser
    void shouldReturnTradeAddPageTest() throws Exception {
        mvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    void shouldReturnTradeValidatePageTest() throws Exception {
        RequestBuilder request = post("/trade/validate").param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity()))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/trade/list"));
    }
    
    @Test
    @WithMockUser
    void shouldGetUpdateTradePage() throws Exception {
        when(tradeService.getById(1)).thenReturn(trade);
        
        mvc.perform(get("/trade/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("trade", hasProperty("account", is("account"))));
    }
    
    @Test
    @WithMockUser
    void shouldUpdateTradeWithTradeDtoTest() throws Exception {
        RequestBuilder request = post("/trade/update/{id}", 1).param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity()))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/trade/list"));
    }
    
    @Test
    @WithMockUser
    void shouldDeleteTradeTest() throws Exception {
        when(tradeService.getById(1)).thenReturn(trade);
        doNothing().when(tradeService)
                .deleteById(1);
        mvc.perform(get("/trade/delete/{id}", 1))
                .andExpect(redirectedUrl("/trade/list"));
    }
}
