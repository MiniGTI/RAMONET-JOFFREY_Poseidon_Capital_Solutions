package com.nnk.springboot.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object to parse data from forms of BidList pages.
 * bidList/add to save a new BidList, managed by the validate method in the BidListController.
 * bidList/update to update an existing BidList, managed by the updateBidList method in the BidListController.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidListDto {
    
    private Integer id;
    @Size(
            max = 30,
            message = "The account name can not exceed 30 characters.")
    private String account;
    @Size(
            max = 30,
            message = "The type name can not exceed 30 characters.")
    private String type;
    private Double bidQuantity;
}
