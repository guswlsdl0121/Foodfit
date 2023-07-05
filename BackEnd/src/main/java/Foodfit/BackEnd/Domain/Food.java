package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "foods")
public class Food {


    @Builder
    private Food(String name, int calorie, double protein, double fat, double salt) {
        this.name = name;
        this.calorie = calorie;
        this.protein = protein;
        this.fat = fat;
        this.salt = salt;
    }

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