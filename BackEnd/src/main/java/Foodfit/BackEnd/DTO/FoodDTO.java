package Foodfit.BackEnd.DTO;

import Foodfit.BackEnd.Domain.Food;

import java.util.List;
import java.util.stream.Collectors;


public record FoodDTO(Food foods) {
    //Food의 List -> FoodDTO의 List로 변환
    public static List<FoodDTO> toList(List<Food> foods) {
        return foods.stream()
                .map(FoodDTO::new)
                .collect(Collectors.toList());
    }
}
