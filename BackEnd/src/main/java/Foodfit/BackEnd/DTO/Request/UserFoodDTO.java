package Foodfit.BackEnd.DTO.Request;

import java.util.List;

public record UserFoodDTO(List<UserFoodItemDTO> list) {

    public record UserFoodItemDTO(Long foodid, Double weight) {}

}

