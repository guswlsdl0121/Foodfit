package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.AnalysisDTO;
import Foodfit.BackEnd.DTO.PeriodAnalysisDTO;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;

import Foodfit.BackEnd.Exception.NotFoundException.NoUserException;
import Foodfit.BackEnd.Exception.NotFoundException.NoFoodException;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisService {
    private final UserFoodRepository userFoodRepository;
    private final UserRepository userRepository;
    private final DatabaseLogger databaseLogger;

    /**
     * methodName : getDailyAnalysis
     * author : junha
     * description : 당일날 사용자가 섭취한 영양분을 반환한다.
     * @param  userDTO
     * @return DailyAnalysisDTO
     */
    public AnalysisDTO getDailyAnalysis(UserDTO userDTO){
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime todayStart = LocalDateTime.of(today, LocalTime.MIN);
            LocalDateTime todayEnd = LocalDateTime.of(today, LocalTime.MAX);

            User user = userRepository.findById(userDTO.getId()).orElseThrow(NoUserException::new);

            List<UserFood> userFoods = userFoodRepository.findByUserAndDateBetween(user, todayStart, todayEnd);

            return calculateDailyAnalysis(userFoods);
        } catch(NoUserException | NoFoodException e){
            databaseLogger.saveLog(e);
            return null;
        }
    }

    private AnalysisDTO calculateDailyAnalysis(List<UserFood> userFoods) {
        int totalCalorie = 0;
        double totalProtein = 0.0;
        double totalFat = 0.0;
        double totalSalt = 0.0;

        for (UserFood userFood : userFoods) {
            Food food = userFood.getFood();
            if (food == null) throw new NoFoodException();

            double weightRatio = userFood.getWeight() / 100;

            totalCalorie += food.getCalorie() * weightRatio;
            totalProtein += Math.round(food.getProtein() * weightRatio);
            totalFat += Math.round(food.getFat() * weightRatio);
            totalSalt += Math.round(food.getSalt() * weightRatio);
        }

        return new AnalysisDTO(totalCalorie, totalProtein, totalFat, totalSalt);
    }

    /**
     * methodName : getPeriodAnalysis
     * author : guswlsdl
     * description : 기간을 입력받고 그에 해당하는 사용자가 섭취한 영양분을 반환한다.
     * @param  userDTO, startDate, endDate, nutrient
     * @return nutrientList
     */
    public List<PeriodAnalysisDTO> getPeriodAnalysis(UserDTO userDTO, LocalDate startDate, LocalDate endDate, String nutrient) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
        List<UserFood> userFoods = userFoodRepository.findByUserAndDateBetween(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        Map<LocalDate, Double> nutrientMap = new HashMap<>();

        for (UserFood userFood : userFoods) {
            LocalDate date = userFood.getDate().toLocalDate();
            double nutrientAmount = getDayAmount(userFood, nutrient);
            nutrientMap.put(date, nutrientMap.getOrDefault(date, 0.0) + nutrientAmount);
        }

        List<PeriodAnalysisDTO> nutrientList = new ArrayList<>();

        for (Map.Entry<LocalDate, Double> entry : nutrientMap.entrySet()) {
            LocalDate date = entry.getKey();
            Double nutrientAmount = entry.getValue();
            PeriodAnalysisDTO dto = new PeriodAnalysisDTO(date, nutrientAmount);
            nutrientList.add(dto);
        }

        return nutrientList;
    }

    /**
     * methodName : getDayAmount
     * author : guswlsdl
     * description : 사용자가 하루동안 먹은 음식의 영양분을 조회한다.
     * @param  userFood, nutrient
     * @return 없음
     */
    private double getDayAmount(UserFood userFood, String nutrient) {
        Food food = userFood.getFood();
        double weightRatio = userFood.getWeight() / 100;

        return switch (nutrient) {
            case "protein" -> food.getProtein() * weightRatio;
            case "fat" -> food.getFat() * weightRatio;
            case "salt" -> food.getSalt() * weightRatio;
            case "caloroie" -> food.getCalorie() * weightRatio;
            // Handle other nutrients accordingly
            default -> throw new IllegalArgumentException("Invalid nutrient type: " + nutrient);
        };
    }
}
