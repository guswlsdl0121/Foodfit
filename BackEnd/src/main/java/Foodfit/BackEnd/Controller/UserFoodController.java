package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.DTO.UserFoodDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Service.UserFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-foods")
@RequiredArgsConstructor
public class UserFoodController {
    private final UserFoodService userFoodService;

    @PostMapping
    public ResponseEntity<UserFood> addUserFood(@RequestBody UserFoodDTO userFoodDTO) {
        UserFood userFood = userFoodService.addUserFood(userFoodDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userFood);
    }
}

