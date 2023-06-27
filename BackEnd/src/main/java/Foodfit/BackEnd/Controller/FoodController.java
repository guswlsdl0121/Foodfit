package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Service.FoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/food-search")
    public List<Food> searchFoods(@RequestParam("name") String name) {
        return foodService.searchFoods(name);
    }
}
