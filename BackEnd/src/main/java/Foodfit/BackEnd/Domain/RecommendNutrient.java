package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommend_nutrient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendNutrient {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nutrient_type")
    private String nutrientType;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "start_age")
    private Integer startAge;

    @Column(name = "end_age")
    private Integer endAge;

    @Column(name = "value")
    private Double value;
}