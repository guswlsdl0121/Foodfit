package Foodfit.BackEnd.DTO;

public record TotalNutrient(
        int totalCalorie,
        double totalProtein,
        double totalFat,
        double totalSalt) {

    public static TotalNutrient add(TotalNutrient totalNutrient, TotalNutrient other) {
        int newTotalCalorie = totalNutrient.totalCalorie() + other.totalCalorie();
        double newTotalProtein = totalNutrient.totalProtein() + other.totalProtein();
        double newTotalFat = totalNutrient.totalFat() + other.totalFat();
        double newTotalSalt = totalNutrient.totalSalt() + other.totalSalt();

        return new TotalNutrient(newTotalCalorie, newTotalProtein, newTotalFat, newTotalSalt);
    }
}


