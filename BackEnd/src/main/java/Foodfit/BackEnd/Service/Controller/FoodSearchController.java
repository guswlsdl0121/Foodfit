package Foodfit.BackEnd.Service.Controller;

import Foodfit.BackEnd.DTO.Response.FoodSearchResponse;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Service.FoodSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "검색 기능 API", description = "사용자가 음식을 검색합니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FoodSearchController {

    private final FoodSearchService foodSearchService;

    @Operation(description = "키워드로 음식을 검색하면 상위 10개의 검색 결과를 반환합니다.")
    @GetMapping("/food")
    public FoodSearchResponse searchFoods(@RequestParam("name") String name) {

        List<Food> foods = foodSearchService.searchFoods(name);

        List<FoodSearchResponse.FoodDTO> foodDTOs = foods.stream()
                .map(FoodSearchResponse.FoodDTO::fromFood)
                .collect(Collectors.toList());

        return new FoodSearchResponse(foods.size(), foodDTOs);
    }
}