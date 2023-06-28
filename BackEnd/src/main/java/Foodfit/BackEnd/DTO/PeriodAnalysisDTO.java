package Foodfit.BackEnd.DTO;

import java.time.LocalDate;

public record PeriodAnalysisDTO(LocalDate date, double totalNutrientAmount) {
}
