package com.nnk.springboot.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object to parse data from forms of Trade pages.
 * trade/add to save a new Trade, managed by the validate method in the TradeController.
 * trade/update to update an existing Trade, managed by the updateTrade method in the TradeController.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeDto {
    
    private int id;
    @Size(
            max = 30,
            message = "The account name can not exceed 30 characters.")
    private String account;
    @Size(
            max = 30,
            message = "The type name can not exceed 30 characters.")
    private String type;
    @Column(name = "buy_quantity")
    private Double buyQuantity;
}
