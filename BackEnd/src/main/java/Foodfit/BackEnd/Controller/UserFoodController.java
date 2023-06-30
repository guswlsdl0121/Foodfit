package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.DTO.UserFoodDTO;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Service.UserFoodService;
import jakarta.servlet.http.HttpServletRequest;
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
    @AdditionalUserInfoCheck
    public ResponseEntity<List<UserFood>> addUserFood(HttpServletRequest request, @RequestBody UserFoodDTO userFoodDTO) {
        User user = (User)request.getAttribute("user");
        List<UserFood> userFoods = userFoodService.addUserFoods(userFoodDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userFoods);
    }


}
