package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.Request.UserFoodDTO;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Repository.FoodRepository;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserFoodService {
    private final UserFoodRepository userFoodRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    /**
     * methodName : addUserFoods
     * author : guswlsdl
     * description : 사용자가 먹은 음식을 테이블에 추가한다.
     * @param  foodIds, weights, userDTO
     * @return 없음
     */
    @Transactional
    public List<UserFood> addUserFoods(List<Long> foodIds, List<Double> weights, UserDTO userDTO) {
        log.info("foodIds = {}", foodIds);
        log.info("weights = {}", weights);

        User user = userRepository.findById(userDTO.getId()).orElseThrow(NoSuchElementException::new);
        // Food 조회
        List<Food> foods = foodRepository.findAllById(foodIds);

        // 현재 시간
        LocalDateTime date = LocalDateTime.now();

        // UserFood 생성 및 저장
        List<UserFood> userFoods = new ArrayList<>();

        if (foodIds.size() != weights.size()) {
            throw new IllegalArgumentException("food의 개수와 weight의 개수가 같아야 합니다.");
        }

        for (int i = 0; i < foodIds.size(); i++) {
            Food food = foods.get(i);
            double weight = weights.get(i);

            UserFood userFood = UserFood.builder()
                    .user(user)
                    .food(food)
                    .date(date)
                    .weight(weight)
                    .build();
            userFoods.add(userFood);
        }

        return userFoodRepository.saveAll(userFoods);
    }
}