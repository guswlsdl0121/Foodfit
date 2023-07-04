package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.FoodRepository;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static Foodfit.BackEnd.Exception.NotFoundException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddUserFoodServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private UserFoodRepository userFoodRepository;

    @InjectMocks
    private AddUserFoodService addUserFoodService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUserFoods_Success() {
        // Mock 데이터 설정
        Long userId = 1L;
        List<Long> foodIds = List.of(1L, 2L);
        List<Double> weights = List.of(100.0, 200.0);

        User user = User.builder()
                .id(userId)
                .name("John")
                .build();

        List<Food> foods = new ArrayList<>();
        Food apple = mock(Food.class);
        when(apple.getId()).thenReturn(1L);
        when(apple.getName()).thenReturn("사과");
        when(apple.getCalorie()).thenReturn(100);
        when(apple.getProtein()).thenReturn(1.0);
        when(apple.getFat()).thenReturn(0.5);
        when(apple.getSalt()).thenReturn(0.1);

        Food cake = mock(Food.class);
        when(cake.getId()).thenReturn(2L);
        when(cake.getName()).thenReturn("케이크");
        when(cake.getCalorie()).thenReturn(200);
        when(cake.getProtein()).thenReturn(2.0);
        when(cake.getFat()).thenReturn(1.0);
        when(cake.getSalt()).thenReturn(0.2);

        foods.add(apple);
        foods.add(cake);

        LocalDateTime now = LocalDateTime.now();

        // Mock 객체 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(foodRepository.findAllByIdIn(foodIds)).thenReturn(foods);
        when(userFoodRepository.saveAll(any())).thenReturn(new ArrayList<>());

        // 서비스 메소드 호출
        addUserFoodService.addUserFoods(foodIds, weights, userId);

        // Verify 호출 확인
        verify(userRepository, times(1)).findById(userId);
        verify(foodRepository, times(1)).findAllByIdIn(foodIds);
        verify(userFoodRepository, times(1)).saveAll(any());
    }

    @Test
    public void testAddUserFoods_NoUserException() {
        // Mock 데이터 설정
        Long userId = 1L;
        List<Long> foodIds = List.of(1L, 2L, 3L);
        List<Double> weights = List.of(100.0, 200.0, 150.0);

        // Mock 객체 설정
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // 예외 확인
        assertThrows(NoUserException.class, () -> {
            addUserFoodService.addUserFoods(foodIds, weights, userId);
        });

        // Verify 호출 확인
        verify(userRepository, times(1)).findById(userId);
        verify(foodRepository, never()).findAllByIdIn(any());
        verify(userFoodRepository, never()).saveAll(any());
    }

    @Test
    public void testAddUserFoods_NoFoodException() {
        // Mock 데이터 설정
        Long userId = 1L;
        List<Long> foodIds = List.of(1L, 2L, 3L);
        List<Double> weights = List.of(100.0, 200.0, 150.0);

        User user = User.builder()
                .id(userId)
                .name("John")
                .build();

        // Mock 객체 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(foodRepository.findAllByIdIn(foodIds)).thenReturn(new ArrayList<>());

        // 예외 확인
        assertThrows(NoFoodException.class, () -> {
            addUserFoodService.addUserFoods(foodIds, weights, userId);
        });

        // Verify 호출 확인
        verify(userRepository, times(1)).findById(userId);
        verify(foodRepository, times(1)).findAllByIdIn(foodIds);
        verify(userFoodRepository, never()).saveAll(any());
    }
}