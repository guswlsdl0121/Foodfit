package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "검색 기능 API", description = "사용자가 음식을 검색합니다.")
@RestController
@RequestMapping("/api")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @Operation(description = "키워드로 음식을 검색하면 상위 10개의 검색 결과를 반환합니다.")
    @GetMapping("/food")
    public List<Food> searchFoods(@RequestParam("name") String name) {
        return foodService.searchFoods(name);
    }
}
