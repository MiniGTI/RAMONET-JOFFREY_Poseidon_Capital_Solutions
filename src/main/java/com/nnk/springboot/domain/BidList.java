package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.time.LocalDate;
/**
 * The BidList Object.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bid_list")
public class BidList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String account;
    private String type;
    @Column(name = "bid_quantity")
    private double bidQuantity;
    @Column(name = "ask_quantity")
    private double askQuantity;
    private double bid;
    private double ask;
    private String benchmark;
    @Column(name = "bid_list_date")
    private LocalDate bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
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
    @Column(name = "source_list_id ")
    private String sourceListId;
    private String side;
    
    
    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
    
}
