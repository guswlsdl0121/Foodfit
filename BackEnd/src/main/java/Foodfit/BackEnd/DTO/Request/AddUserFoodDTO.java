package Foodfit.BackEnd.DTO.Request;

import java.util.ArrayList;
import java.util.List;

public record AddUserFoodDTO(List<UserFoodItemDTO> list) {
    public record UserFoodItemDTO(Long foodid, Double weight) {}
    public List<Long> toFoodIdList() {
        List<Long> foodIds = new ArrayList<>();
        for (UserFoodItemDTO item : list) {
            foodIds.add(item.foodid());
        }
        return foodIds;
    }

    public List<Double> toWeightList() {
        List<Double> weights = new ArrayList<>();
        for (UserFoodItemDTO item : list) {
            weights.add(item.weight());
        }
        return weights;
    }
}

