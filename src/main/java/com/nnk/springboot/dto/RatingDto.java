package com.nnk.springboot.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object to parse data from forms of Rating pages.
 * rating/add to save a new Rating, managed by the validate method in the RatingController.
 * rating/update to update an existing Rating, managed by the updateRating method in the RatingController.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {
    
    private Integer id;
    @Size(max = 125, message = "Moodys name can not exceed 125 characters.")
    private String moodys;
    @Size(max = 125, message = "Sand name can not exceed 125 characters.")
    private String sand;
    @Size(max = 125, message = "Ficth name can not exceed 125 characters.")
    private String fitch;
    private Integer order;
    
}
