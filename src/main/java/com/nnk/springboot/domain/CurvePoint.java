package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "curve_point")
public class CurvePoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "curve_id")
    private int curveId;
    @Column(name = "as_of_date")
    private Timestamp asOfDate;
    private double term;
    private double value;
    @Column(name = "creation_date")
    private Timestamp creationDate;
    
    public CurvePoint(double term, double value) {
        this.term = term;
        this.value = value;
    }
}
