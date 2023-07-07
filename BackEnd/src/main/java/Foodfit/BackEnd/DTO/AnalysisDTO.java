package Foodfit.BackEnd.DTO;

import Foodfit.BackEnd.Domain.RecommendNutrient;
import lombok.AllArgsConstructor;

import java.util.List;

public record AnalysisDTO(
        int recommendedCaloriePerDay,
        double recommendedProteinPerDay,
        double recommendedFatPerDay,
        double recommendedSaltPerDay,
        int totalCaloriePerDay,
        double totalProteinPerDay,
        double totalFatPerDay,
        double totalSaltPerDay
) {
    public AnalysisDTO(List<RecommendNutrient> recommendNutrient, int totalCalorie, double totalProtein, double totalFat, double totalSalt) {
        this(
                recommendNutrient.get(0).getNutrientValue().intValue(),
                recommendNutrient.get(1).getNutrientValue().intValue(),
                recommendNutrient.get(2).getNutrientValue().intValue(),
                recommendNutrient.get(3).getNutrientValue().intValue(),
                totalCalorie, totalProtein, totalFat, totalSalt);
    }
    public AnalysisDTO(TotalNutrient totalNutrient) {
        this(2000, 50.0, 70.0, 5.0,
                totalNutrient.totalCalorie(), totalNutrient.totalProtein(), totalNutrient.totalFat(), totalNutrient.totalSalt());
    }

}