package com.nnk.springboot.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @Size(
            max = 30,
            message = "The account name can not exceed 30 characters.")
    private String account;
    @Size(
            max = 30,
            message = "The type name can not exceed 30 characters.")
    private String type;
    @Column(name = "bid_quantity")
    private double bidQuantity;
    @Column(name = "ask_quantity")
    private double askQuantity;
    private double bid;
    private double ask;
    @Size(max = 125)
    private String benchmark;
    @Column(name = "bid_list_date")
    private LocalDate bidListDate;
    @Size(max = 125)
    private String commentary;
    @Size(max = 125)
    private String security;
    @Size(max = 10)
    private String status;
    @Size(max = 125)
    private String trader;
    @Size(max = 125)
    private String book;
    @Size(max = 125)
    @Column(name = "creation_name")
    private String creationName;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Size(max = 125)
    @Column(name = "revision_name")
    private String revisionName;
    @Column(name = "revision_date")
    private LocalDate revisionDate;
    @Size(max = 125)
    @Column(name = "deal_name")
    private String dealName;
    @Size(max = 125)
    @Column(name = "deal_type")
    private String dealType;
    @Size(max = 125)
    @Column(name = "source_list_id ")
    private String sourceListId;
    @Size(max = 125)
    private String side;
}
