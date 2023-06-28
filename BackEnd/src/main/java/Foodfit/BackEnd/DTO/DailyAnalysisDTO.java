package Foodfit.BackEnd.DTO;

public record DailyAnalysisDTO(
        int recommendedCaloriePerDay,
        double recommendedProteinPerDay,
        double recommendedFatPerDay,
        double recommendedSaltPerDay,
        int totalCaloriePerDay,
        double totalProteinPerDay,
        double totalFatPerDay,
        double totalSaltPerDay
) {
    public DailyAnalysisDTO(int totalCalorie, double totalProtein, double totalFat, double totalSalt) {
        this(2000, 50.0, 70.0, 5.0,
                totalCalorie, totalProtein, totalFat, totalSalt);
    }
}