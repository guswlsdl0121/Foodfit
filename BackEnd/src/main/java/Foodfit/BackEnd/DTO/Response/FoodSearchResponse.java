package Foodfit.BackEnd.DTO.Response;

import Foodfit.BackEnd.Domain.Food;

import java.util.List;



public record FoodSearchResponse(int count, List<FoodDTO> searchList) {
    public record FoodDTO(Long id, String name, double calorie, double protein, double fat, double salt) {

        //SearchController에서 Food를 가져오기 위한 메서드
        public static FoodDTO fromFood(Food food) {
            return new FoodDTO(food.getId(), food.getName(), food.getCalorie(), food.getProtein(), food.getFat(), food.getSalt());
        }
    }
}

