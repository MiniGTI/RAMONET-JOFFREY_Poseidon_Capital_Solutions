package com.nnk.springboot.dto;

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
    private String moodysRating;
    private String sandRating;
    private String fitchRating;
    private Integer orderNumber;
    
}
