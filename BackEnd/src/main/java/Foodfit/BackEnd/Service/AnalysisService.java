package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.AnalysisDTO;
import Foodfit.BackEnd.DTO.PeriodAnalysisDTO;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Domain.*;

import Foodfit.BackEnd.Exception.NotFoundException.NoUserException;
import Foodfit.BackEnd.Exception.NotFoundException.NoFoodException;
import Foodfit.BackEnd.Repository.RecommendNutrientRepository;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AnalysisService {
    private final UserFoodRepository userFoodRepository;
    private final UserRepository userRepository;
    private final DatabaseLogger databaseLogger;
    private final RecommendNutrientRepository recommendNutrientRepository;

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

            Gender gender = user.getGender();
            int age = user.getAge();

            List<RecommendNutrient> recommendNutrient = recommendNutrientRepository.findRecommendNutrient(age, gender);

            List<UserFood> userFoods = userFoodRepository.findByUserAndDateBetweenOrderByDateAsc(user, todayStart, todayEnd);

            return calculateDailyAnalysis(userFoods, recommendNutrient);
        } catch(NoUserException | NoFoodException e){
            databaseLogger.saveLog(e);
            return null;
        }
    }

    private AnalysisDTO calculateDailyAnalysis(List<UserFood> userFoods, List<RecommendNutrient> recommendNutrient) {
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

        return new AnalysisDTO(recommendNutrient, totalCalorie, totalProtein, totalFat, totalSalt);
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
        List<UserFood> userFoods = userFoodRepository.findByUserAndDateBetweenOrderByDateAsc(user, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));

        // 날짜 범위 내에서 날짜를 생성하여 기본값인 0으로 초기화
        Map<LocalDate, Double> nutrientMap = new TreeMap<>();

        // 조회된 데이터를 기반으로 실제 데이터 계산
        for (UserFood userFood : userFoods) {
            LocalDate date = userFood.getDate().toLocalDate();
            double nutrientAmount = getDayAmount(userFood, nutrient);
            nutrientMap.put(date, nutrientMap.getOrDefault(date, 0.0) + nutrientAmount);
        }

        // 날짜 범위 내에 존재하지 않는 날짜를 0으로 채움
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            nutrientMap.putIfAbsent(date, 0.0);
        }

        // 결과를 PeriodAnalysisDTO 리스트로 변환
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
