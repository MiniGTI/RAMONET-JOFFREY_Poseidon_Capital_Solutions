package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trade")
public class Trade {
    
    @Column(
            name = "trade_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String account;
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
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    @Column(name = "creation_name")
    private String creationName;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "revision_name")
    private String revisionName;
    @Column(name = "revision_date")
    private LocalDate revisionDate;
    @Column(name = "deal_name")
    private String dealName;
    @Column(name = "deal_type")
    private String dealType;
    @Column(name = "source_list_id")
    private String sourceListId;
    private String side;
    
    
    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
