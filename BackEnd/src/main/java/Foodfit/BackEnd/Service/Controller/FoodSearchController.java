package Foodfit.BackEnd.Service.Controller;

import Foodfit.BackEnd.DTO.FoodDTO;
import Foodfit.BackEnd.DTO.Response.FoodSearchResponse;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Exception.BadRequestException;
import Foodfit.BackEnd.Service.FoodSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "검색 기능 API", description = "사용자가 음식을 검색합니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FoodSearchController {

    private final FoodSearchService foodSearchService;

    @Operation(description = "키워드로 음식을 검색하면 상위 10개의 검색 결과를 반환합니다.")
    @GetMapping("/food")
    public FoodSearchResponse searchFoods(@RequestParam("name") String name) {
        //검색어가 영어인지 확인
        if (containsEnglish(name)) throw new BadRequestException.EnglishSearchException();

        List<Food> foods = foodSearchService.searchFoods(name);

        //엔티티의 list를 DTO의 list로 변환
        List<FoodDTO> foodDTOs = FoodDTO.toList(foods);

        return new FoodSearchResponse(foods.size(), foodDTOs);
    }

    /**
     * methodName : containsEnglish
     * author : guswlsdl
     * description : 검색어에 영어가 포함되어있는지 확인한다.
     * @param  name
     * @return boolean
     */
    private boolean containsEnglish(String name) {
        for (char c : name.toCharArray()) {
            if (Character.isLetter(c) && Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
                return true;
            }
        }
        return false;
    }
}
