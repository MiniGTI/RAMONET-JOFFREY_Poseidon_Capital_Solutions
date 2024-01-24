package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(max = 125, message = "Name can not exceed 125 characters.")
    private String name;
    @Size(max = 125, message = "Description can not exceed 125 characters.")
    private  String description;
    @Size(max = 125, message = "Json can not exceed 125 characters.")
    private  String json;
    @Size(max = 512, message = "Template can not exceed 512 characters.")
    private  String template;
    @Size(max = 125, message = "Sql Str can not exceed 125 characters.")
    @Column(name = "sql_str")
    private String sqlStr;
    @Size(max = 125, message = "Sql Part can not exceed 125 characters.")
    @Column(name = "sql_part")
    private String sqlPart;

}
