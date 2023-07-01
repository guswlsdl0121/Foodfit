package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.DTO.DailyAnalysisDTO;
import Foodfit.BackEnd.DTO.PeriodAnalysisDTO;
import Foodfit.BackEnd.DTO.Response.PeriodAnalysisResponse;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "일일, 기간별 식단 분석 API", description = "사용자가 등록한 식단을 토대로 일일, 기간별 영양 분석을 제공합니다.")
@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@Slf4j
public class AnalysisController {
    private final AnalysisService analysisService;

    @Operation(summary = "일일분석")
    @GetMapping("/daily")
    @AdditionalUserInfoCheck
    public ResponseEntity<DailyAnalysisDTO> makeDailyAnalysis(HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        DailyAnalysisDTO daily_analysis = analysisService.getDailyAnalysis(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(daily_analysis);
    }

    @Operation(summary = "기간별 분석", description="쿼리 파라미터를 통해 시작 날짜, 종료 날짜, 영양분을 보내주어야 합니다.\n" +
            "영양분으로는 protein(단백질), fat(지방), salt(나트륨), caloroie(열량)이 있습니다.")
    @GetMapping("/period")
    @AdditionalUserInfoCheck
    public ResponseEntity<PeriodAnalysisResponse> makePeriodAnalysis(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("nutrient") String nutrient,
            HttpServletRequest request
    ) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        List<PeriodAnalysisDTO> nutrientList = analysisService.getPeriodAnalysis(userDTO, startDate, endDate, nutrient);
        PeriodAnalysisResponse response = new PeriodAnalysisResponse(nutrientList);
        return ResponseEntity.ok(response);
    }
}


