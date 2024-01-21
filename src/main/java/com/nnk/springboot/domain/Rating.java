package com.nnk.springboot.domain;

import jakarta.persistence.*;
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
    @Column(name = "moodys_rating")
    private String moodysRating;
    @Column(name = "sand_rating")
    private String sandRating;
    @Column(name = "fitch_rating")
    private String fitchRating;
    @Column(name = "order_number")
    private Integer orderNumber;
}
