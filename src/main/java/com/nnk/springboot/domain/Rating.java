package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating")
public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "moodys_rating")
    private String moodysRating;
    @Column(name = "sand_rating")
    private String sandRating;
    @Column(name = "fitch_rating")
    private String fitchRating;
    @Column(name = "order_number")
    private int orderNumber;
    
    public Rating(String moodysRating, String sandRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandRating = sandRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
