package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.DTO.DailyAnalysisDTO;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Service.AnalysisService;
import Foodfit.BackEnd.Utils.UserProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @Operation(summary = "일일분석")
    @GetMapping("/daily")
    public ResponseEntity<DailyAnalysisDTO> makeDailyAnalysis(){
        DailyAnalysisDTO daily_analysis = analysisService.makeDailyAnalysis();
        return ResponseEntity.status(HttpStatus.CREATED).body(daily_analysis);

    }

}
