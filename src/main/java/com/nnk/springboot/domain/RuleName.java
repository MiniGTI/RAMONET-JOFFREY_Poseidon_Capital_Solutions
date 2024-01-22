package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * The RuleName Object.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rule_name")
public class RuleName {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private  String description;
    private  String json;
    private  String template;
    @Column(name = "sql_str")
    private String sqlStr;
    @Column(name = "sql_part")
    private String sqlPart;

}
