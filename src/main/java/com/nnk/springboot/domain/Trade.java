package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * The Trade object;
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trade")
public class Trade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(max = 30, message = "The account name can not exceed 30 characters.")
    private String account;
    @Size(max = 30, message = "The type name can not exceed 30 characters.")
    private String type;
    @Column(name = "buy_quantity")
    private double buyQuantity;
    @Column(name = "sell_quantity ")
    private double sellQuantity;
    @Column(name = "buy_price")
    private double buyPrice;
    @Column(name = "sell_price")
    private double sellPrice;
    @Column(name = "trade_date")
    private LocalDate tradeDate;
    @Size(max = 125, message = "Security can not exceed 125 characters.")
    private String security;
    @Size(max = 10, message = "Status can not exceed 10 characters.")
    private String status;
    @Size(max = 125, message = "Trader can not exceed 125 characters.")
    private String trader;
    @Size(max = 125, message = "Benchmark can not exceed 125 characters.")
    private String benchmark;
    @Size(max = 125, message = "Book can not exceed 125 characters.")
    private String book;
    @Column(name = "creation_name")
    @Size(max = 125, message = "Creation Name can not exceed 125 characters.")
    private String creationName;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "revision_name")
    @Size(max = 125, message = "Revision Name can not exceed 125 characters.")
    private String revisionName;
    @Column(name = "revision_date")
    private LocalDate revisionDate;
    @Column(name = "deal_name")
    @Size(max = 125, message = "Deal Name can not exceed 125 characters.")
    private String dealName;
    @Column(name = "deal_type")
    @Size(max = 125, message = "Deal Type can not exceed 125 characters.")
    private String dealType;
    @Column(name = "source_list_id")
    @Size(max = 125, message = "Source List Id can not exceed 125 characters.")
    private String sourceListId;
    @Size(max = 125, message = "Side can not exceed 125 characters.")
    private String side;

}
