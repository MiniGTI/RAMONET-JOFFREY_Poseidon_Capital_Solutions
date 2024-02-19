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


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating")
public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(
            max = 125,
            message = "Moodys name can not exceed 125 characters.")
    private String moodys;
    @Size(
            max = 125,
            message = "Sand name can not exceed 125 characters.")
    private String sand;
    @Size(
            max = 125,
            message = "Ficth name can not exceed 125 characters.")
    private String fitch;
    @Column(name = "order_rating")
    private Integer order;
}
