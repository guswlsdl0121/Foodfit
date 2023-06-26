package Foodfit.BackEnd.DTO;

//userId의 경우 로그인 구현이 끝나면 수정해야됨
public record UserFoodDTO(Long userId, Long foodId, double weight) {}

