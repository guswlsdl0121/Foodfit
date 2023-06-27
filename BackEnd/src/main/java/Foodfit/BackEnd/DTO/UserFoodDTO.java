package Foodfit.BackEnd.DTO;

import java.util.List;

public record UserFoodDTO(List<Long> foodIds,  List<Double> weights) {}

