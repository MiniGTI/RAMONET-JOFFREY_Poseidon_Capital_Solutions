package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;
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

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private RatingService ratingService;
    @MockBean
    private UserService userService;
    private final Rating rating = new Rating(1, "moodys", "sand", "fitch", 50);
    private final RatingDto ratingDto = RatingDto.builder()
            .moodys("newMoodys")
            .sand("newRating")
            .fitch("newFitch")
            .order(55)
            .build();
    private final List<Rating> ratings = new ArrayList<>(List.of(rating, new Rating()));
    @Test
    @WithMockUser
    void shouldReturnRatingListPageTest() throws Exception {
        when(ratingService.getAll()).thenReturn(ratings);
        when(userService.getUserName(any())).thenReturn("fullname");
        mvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fullname", equalTo("fullname")))
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
        RequestBuilder request = post("/rating/validate").param("moodysRating", ratingDto.getMoodys())
                .param("sand", ratingDto.getSand())
                .param("fitch", ratingDto.getFitch())
                .param("order", String.valueOf(ratingDto.getOrder()))
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
                .andExpect(model().attribute("rating", hasProperty("moodys", is("moodys"))));
    }
    
    @Test
    @WithMockUser
    void shouldUpdateRatingWithRatingDtoTest() throws Exception {
        RequestBuilder request = post("/rating/update/{id}", 1).param("moodysRating", ratingDto.getMoodys())
                .param("sand", ratingDto.getSand())
                .param("fitch", ratingDto.getFitch())
                .param("order", String.valueOf(ratingDto.getOrder()))
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
