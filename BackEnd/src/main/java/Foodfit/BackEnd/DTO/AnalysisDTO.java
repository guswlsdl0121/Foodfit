package Foodfit.BackEnd.DTO;

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
    public AnalysisDTO(int totalCalorie, double totalProtein, double totalFat, double totalSalt) {
        this(2000, 50.0, 70.0, 5.0,
                totalCalorie, totalProtein, totalFat, totalSalt);
    }
    public AnalysisDTO(TotalNutrient totalNutrient) {
        this(2000, 50.0, 70.0, 5.0,
                totalNutrient.totalCalorie(), totalNutrient.totalProtein(), totalNutrient.totalFat(), totalNutrient.totalSalt());
    }
}