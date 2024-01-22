package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.Model;

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
public class RatingControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private RatingService ratingService;
    
    private final Rating rating = new Rating(1, "moodys", "sand", "fitch", 50);
    private final RatingDto ratingDto = RatingDto.builder()
            .moodysRating("newMoodys")
            .sandRating("newRating")
            .fitchRating("newFitch")
            .orderNumber(55)
            .build();
    private final List<Rating> ratings = new ArrayList<>(List.of(rating, new Rating()));
    
    @Test
    @WithMockUser
    void shouldReturnRatingListPageTest() throws Exception {
        when(ratingService.getAll()).thenReturn(ratings);
        
        mvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ratings", hasSize(2)));
    }
    
    @Test
    @WithMockUser
    void shouldReturnRatingAddPageTest() throws Exception {
        mvc.perform(get("/rating/add"))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    void shouldReturnRatingValidatePageTest() throws Exception {
        RequestBuilder request = post("/rating/validate").param("moodysRating", ratingDto.getMoodysRating())
                .param("sandRating", ratingDto.getSandRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber()))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/rating/list"));
    }
    
    @Test
    @WithMockUser
    void shouldGetUpdateRatingPage() throws Exception {
        when(ratingService.getById(1)).thenReturn(rating);
        
        mvc.perform(get("/rating/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("rating", hasProperty("moodysRating", is("moodys"))));
    }
    
    @Test
    @WithMockUser
    void shouldUpdateRatingWithRatingDtoTest() throws Exception {
        RequestBuilder request = post("/rating/update/{id}", 1).param("moodysRating", ratingDto.getMoodysRating())
                .param("sandRating", ratingDto.getSandRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber()))
                .with(csrf());
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/rating/list"));
    }
    
    @Test
    @WithMockUser
    void shouldDeleteRatingTest() throws Exception {
        when(ratingService.getById(1)).thenReturn(rating);
        doNothing().when(ratingService)
                .deleteById(1);
        mvc.perform(get("/rating/delete/{id}", 1))
                .andExpect(redirectedUrl("/rating/list"));
    }
}
