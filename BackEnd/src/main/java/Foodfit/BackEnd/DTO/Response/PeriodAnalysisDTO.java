package Foodfit.BackEnd.DTO.Response;

import java.time.LocalDate;

public record PeriodAnalysisDTO(LocalDate date, double totalNutrientAmount) {
}
