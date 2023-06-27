package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.DTO.UserFoodDTO;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Service.UserFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-foods")
@RequiredArgsConstructor
public class UserFoodController {
    private final UserFoodService userFoodService;

    @PostMapping
    public ResponseEntity<List<UserFood>> addUserFood(@RequestBody UserFoodDTO userFoodDTO) {
        List<UserFood> userFoods = userFoodService.addUserFoods(userFoodDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userFoods);
    }
}

