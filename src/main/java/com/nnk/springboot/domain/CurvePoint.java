package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "curve_point")
public class CurvePoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "curve_id")
    private int curveId;
    @Column(name = "as_of_date")
    private LocalDate asOfDate;
    @NotNull(message = "Term can't be null, enter 0.")
    private Double term;
    @NotNull(message = "Value can't be null, enter 0.")
    private Double value;
    @Column(name = "creation_date")
    private LocalDate creationDate;
}
