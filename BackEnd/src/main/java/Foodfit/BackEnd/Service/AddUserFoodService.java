package Foodfit.BackEnd.Service;

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

import static Foodfit.BackEnd.Exception.NotFoundException.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AddUserFoodService {
    private final UserFoodRepository userFoodRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    /**
     * methodName : addUserFoods
     * author : guswlsdl
     * description : 사용자가 먹은 음식을 테이블에 추가한다.
     * @param foodIds, weights, userDTO
     */
    @Transactional
    public void addUserFoods(List<Long> foodIds, List<Double> weights, Long userId) {
        // User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(NoUserException::new);

        // Food 조회
        List<Food> foods = foodRepository.findAllByIdIn(foodIds);

        //Food 예외처리
        if (foods.isEmpty()) throw new NoFoodException();

        // UserFood를 저장할 List 생성
        List<UserFood> userFoods = createUserFoodList(foodIds, weights, user, foods);

        userFoodRepository.saveAll(userFoods);
    }

    /**
     * methodName : createUserFoodList
     * author : guswlsdl
     * description : 리스트 생성 메서드
     * @param foodIds, weights, user, foods
     */
    private  List<UserFood> createUserFoodList(List<Long> foodIds, List<Double> weights, User user, List<Food> foods) {
        List<UserFood> userFoods = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now();

        int foodIdsSize = foodIds.size();

        for (int i = 0; i < foodIdsSize; i++) {
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
        return userFoods;
    }
}