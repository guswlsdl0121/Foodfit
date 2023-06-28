package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.DTO.UserFoodDTO;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Service.UserFoodService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
