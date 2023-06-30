package Foodfit.BackEnd.DTO.Response;

import java.util.List;



public record FoodSearchResponse(int count, List<FoodDTO> searchlist) {
    public record FoodDTO(Long id, String name, double calorie, double protein, double fat, double salt) {}
}

