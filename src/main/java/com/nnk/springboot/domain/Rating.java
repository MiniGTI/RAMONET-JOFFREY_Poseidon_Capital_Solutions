package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * The Rating Object.
 */
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
    @Size(max = 125, message = "Moodys name can not exceed 125 characters.")
    private String moodys;
    @Size(max = 125, message = "Sand name can not exceed 125 characters.")
    private String sand;
    @Size(max = 125, message = "Ficth name can not exceed 125 characters.")
    private String fitch;
    @Column(name = "order_rating")
    private Integer order;
}
