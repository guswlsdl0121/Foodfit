package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;
    private String name;
    private int calorie;
    private double protein;
    private double fat;
    private double salt;
}