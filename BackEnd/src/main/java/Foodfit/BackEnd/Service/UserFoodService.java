package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.UserFoodDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Repository.FoodRepository;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Utils.UserProvider;
import lombok.RequiredArgsConstructor;
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
    private final UserProvider userProvider;

    @Transactional
    public List<UserFood> addUserFoods(UserFoodDTO userFoodDTO) {
        List<Long> foodIds = userFoodDTO.foodIds();
        List<Double> weights = userFoodDTO.weights();
        User user = userProvider.getUserFromSecurityContext()
                .orElseThrow(() -> new NoSuchElementException("사용자가 없습니다."));

        log.info("사용자 pk : {}", user.getId());

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
