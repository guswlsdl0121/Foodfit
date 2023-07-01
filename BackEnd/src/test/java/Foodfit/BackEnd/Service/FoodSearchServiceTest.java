package Foodfit.BackEnd.Service;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Exception.BadRequestException;
import Foodfit.BackEnd.Exception.NotFoundException;
import Foodfit.BackEnd.Repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Foodfit.BackEnd.Exception.NotFoundException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class FoodSearchServiceTest {

    private FoodSearchService foodSearchService;
    private FoodRepository foodRepository;

    @BeforeEach
    void setup() {
        foodRepository = mock(FoodRepository.class);
        foodSearchService = new FoodSearchService(foodRepository);
    }

    @Test
    void testSearchFoods() {
        // Mocked data
        List<Food> foods = new ArrayList<>();
        Food apple = mock(Food.class);
        when(apple.getId()).thenReturn(1L);
        when(apple.getName()).thenReturn("사과");
        when(apple.getCalorie()).thenReturn(100);
        when(apple.getProtein()).thenReturn(1.0);
        when(apple.getFat()).thenReturn(0.5);
        when(apple.getSalt()).thenReturn(0.1);
        foods.add(apple);


        //foodRepository의 findByNameContainingIgnoreCase() 메서드를 호출할 때, "사과"라는 값을 포함하는 이름을 가진 Food 객체를 찾는 기능을 모킹
        when(foodRepository.findTop10ByNameContainingIgnoreCase(eq("사과"))).thenReturn(foods);


        // Calling the method to be tested
        List<Food> result = foodSearchService.searchFoods("사과");

        // Assertion
        assertEquals(foods, result);
    }

    @Test
    void testSearchFoods_NoSearchResultException() {
        // Mocking repository method
        when(foodRepository.findTop10ByNameContainingIgnoreCase(eq("사과"))).thenReturn(Collections.emptyList());

        // Calling the method to be tested and asserting the exception
        assertThrows(NoFoodException.class, () -> foodSearchService.searchFoods("사과"));
    }

    @Test
    void testSearchFoods_EnglishSearchException() {
        // Mocking repository method
        when(foodRepository.findTop10ByNameContainingIgnoreCase(eq("사과"))).thenReturn(Collections.emptyList());

        // Calling the method to be tested and asserting the exception
        assertThrows(BadRequestException.EnglishSearchException.class, () -> foodSearchService.searchFoods("apple"));
    }
}