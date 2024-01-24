package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * The CurvePoint object.
 */
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
    private Double term;
    private Double value;
    @Column(name = "creation_date")
    private LocalDate creationDate;
}
