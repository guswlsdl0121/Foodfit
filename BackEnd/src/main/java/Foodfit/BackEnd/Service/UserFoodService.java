package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.UserFoodDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Repository.FoodRepository;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFoodService {
    private final UserFoodRepository userFoodRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public UserFood addUserFood(UserFoodDTO userFoodDTO) {
        // DTO에서 필요한 정보 가져오기
        Long userId = userFoodDTO.userId();
        Long foodId = userFoodDTO.foodId();
        double weight = userFoodDTO.weight();

        // User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Food 조회
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found with id: " + foodId));

        // 현재 시간
        LocalDateTime date = LocalDateTime.now();

        // UserFood 생성
        UserFood userFood = UserFood.builder()
                .user(user)
                .food(food)
                .date(date)
                .weight(weight)
                .build();

        // UserFood 저장
        return userFoodRepository.save(userFood);
    }
}

