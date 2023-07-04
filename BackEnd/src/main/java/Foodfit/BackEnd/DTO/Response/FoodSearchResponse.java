package Foodfit.BackEnd.DTO.Response;

import Foodfit.BackEnd.DTO.FoodDTO;

import java.util.List;



public record FoodSearchResponse(int count, List<FoodDTO> searchList) {
}

