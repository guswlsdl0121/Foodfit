package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.DTO.Request.UserFoodDTO;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Service.AddUserFoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "식단 등록 API", description = "사용자가 식단을 등록하는 기능을 제공합니다.")
@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class AddUserFoodController {
    private final AddUserFoodService addUserFoodService;

    @PostMapping
    @AdditionalUserInfoCheck
    @Operation(description = "식단 추가하기에서 검색 결과로 반환된 음식의 ID와 그 음식의 중량을 요청으로 보내주어야 합니다.")
    public ResponseEntity<List<UserFood>> addUserFood(HttpServletRequest request, @RequestBody UserFoodDTO userFoodDTO) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        List<UserFoodDTO.UserFoodItemDTO> userFoodItems = userFoodDTO.list();
        List<Long> foodIds = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for (UserFoodDTO.UserFoodItemDTO item : userFoodItems) {
            foodIds.add(item.foodid());
            weights.add(item.weight());
        }
        addUserFoodService.addUserFoods(foodIds, weights, userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
