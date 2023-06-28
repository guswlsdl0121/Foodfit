package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.DailyAnalysisDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisService {
    private final UserFoodRepository userFoodRepository;

    public DailyAnalysisDTO makeDailyAnalysis(Long user_id){
        // 현재시간
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(today, LocalTime.MAX);

        // UserFood 조회
        List<UserFood> userFoods = Optional.ofNullable(userFoodRepository.findAllByUserIdAndDateBetween(user_id, todayStart, todayEnd))
                .orElse(new ArrayList<>());

        // 영양소
        int totalCalorie = 0;
        double totalProtein = 0.0;
        double totalFat = 0.0;
        double totalSalt = 0.0;

        for (UserFood userFood : userFoods){
            Food food = userFood.getFood();
            double weightRatio = userFood.getWeight() / 100;

            totalCalorie += food.getCalorie()*weightRatio;
            totalProtein += Math.round(food.getProtein()*weightRatio);
            totalFat += Math.round(food.getFat()*weightRatio);
            totalSalt += Math.round(food.getSalt()*weightRatio);

        }

        return new DailyAnalysisDTO(totalCalorie, totalProtein, totalFat, totalSalt);

    }

}
