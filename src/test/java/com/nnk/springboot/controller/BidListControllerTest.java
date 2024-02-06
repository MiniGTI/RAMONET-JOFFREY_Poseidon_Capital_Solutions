package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private BidListService bidListService;
    @MockBean
    private UserService userService;
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
    @WithMockUser
    void shouldReturnBidListListPageTest() throws Exception {
        when(bidListService.getAll()).thenReturn(bidLists);
        when(userService.getUserName(any())).thenReturn("fullname");
        mvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fullname", equalTo("fullname")))
                .andExpect(model().attribute("bidLists", hasSize(2)));
    }
    
    @Test
    @WithMockUser
    void shouldReturnBidListAddPageTest() throws Exception {
        mvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    void shouldReturnBidListValidateFormTest() throws Exception {
        bidListService.save(bidListDto);
        RequestBuilder request = post("/bidList/validate").param("account", bidListDto.getAccount())
                .param("type", bidListDto.getType())
                .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity()))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/bidList/list"));
    }
    
    @Test
    @WithMockUser
    void shouldReturnUpdateBidListPage() throws Exception {
        when(bidListService.getById(1)).thenReturn(bidList);
        
        mvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bidList", hasProperty("account", is("account"))));
    }
    
    @Test
    @WithMockUser
    void shouldUpdateBidListWithBidListDtoTest() throws Exception {
        when(bidListService.update(bidListDto)).thenReturn(bidList);
        
        RequestBuilder request = post("/bidList/update/{id}", bidList.getId()).param("account", "new Account")
                .with(csrf());
        
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(redirectedUrl("/bidList/list"));
    }
    
    @Test
    @WithMockUser
    void shouldDeleteBidListTest() throws  Exception{
        doNothing().when(bidListService).deleteById(1);
        mvc.perform(get("/bidList/delete/{id}", 1)).andExpect(redirectedUrl("/bidList/list"));
    }
}
