package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bid_list")
public class BidList {
    
    @Column(name = "bid_list_id")
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
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    @Column(name = "creation_name")
    private String creationName;
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Column(name = "revision_name")
    private String revisionName;
    @Column(name = "revision_date")
    private Timestamp revisionDate;
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
