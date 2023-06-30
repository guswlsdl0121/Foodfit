package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.DTO.DailyAnalysisDTO;
import Foodfit.BackEnd.DTO.Response.PeriodAnalysisDTO;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@Slf4j
public class AnalysisController {
    private final AnalysisService analysisService;

    @Operation(summary = "일일분석")
    @GetMapping("/daily")
    @AdditionalUserInfoCheck
    public ResponseEntity<DailyAnalysisDTO> makeDailyAnalysis(HttpServletRequest request){
        UserDTO userDTO = (UserDTO)request.getAttribute("user");
        DailyAnalysisDTO daily_analysis = analysisService.getDailyAnalysis(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(daily_analysis);
    }

    @GetMapping("/period")
    @AdditionalUserInfoCheck
    public ResponseEntity<List<PeriodAnalysisDTO>> getUserFoodNutrientAmount(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("nutrient") String nutrient,
            HttpServletRequest request
    ) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        List<PeriodAnalysisDTO> nutrientList = analysisService.getPeriodAnalysis(userDTO, startDate, endDate, nutrient);
        return ResponseEntity.ok(nutrientList);
    }
}


