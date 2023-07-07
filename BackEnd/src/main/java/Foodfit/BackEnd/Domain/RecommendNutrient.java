package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendNutrient {

    public RecommendNutrient(String nutrientType, Gender gender, Integer startAge, Integer endAge, Double nutrientValue) {
        this.nutrientType = nutrientType;
        this.gender = gender;
        this.startAge = startAge;
        this.endAge = endAge;
        this.nutrientValue = nutrientValue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nutrientType;


    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer startAge;

    private Integer endAge;

    private Double nutrientValue;
}