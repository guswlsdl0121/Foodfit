package Foodfit.BackEnd.DTO;

import java.util.List;

//userId의 경우 로그인 구현이 끝나면 수정해야됨
public record UserFoodDTO(Long userId, List<Long> foodIds,  List<Double> weights) {}

