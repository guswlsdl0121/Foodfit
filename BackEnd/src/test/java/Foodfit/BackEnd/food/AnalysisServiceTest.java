package Foodfit.BackEnd.food;

import Foodfit.BackEnd.DTO.AnalysisDTO;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import Foodfit.BackEnd.Service.AnalysisService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AnalysisServiceTest {

    @InjectMocks
    private AnalysisService analysisService;

    @Mock
    private UserFoodRepository userFoodRepository;

    @Mock
    private UserRepository userRepository;


    @Test
    void InjectionTest() {
        assertNotNull(userRepository);
        assertNotNull(userFoodRepository);
        assertNotNull(analysisService);
    }

    @Test
    void testGetDailyAnalysis() {
        // Mock data
        User user = new User(1L, "tester", 22, Gender.MALE, 2134L, null);
        UserDTO userDTO = new UserDTO(1L, "tester", 22, Gender.MALE, null);

        // Mock UserFood objects
        List<UserFood> userFoods = new ArrayList<>();
        UserFood mockUserFood1 = createMockUserFood(200, 200, 10.0, 5.0, 2.0);
        userFoods.add(mockUserFood1);

        UserFood mockUserFood2 = createMockUserFood(300, 300, 15.0, 8.0, 3.0);
        userFoods.add(mockUserFood2);

        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(user));
        when(userFoodRepository.findByUserAndDateBetween(eq(user), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(userFoods);

        // Expected result
        AnalysisDTO expected = new AnalysisDTO(1300, 65.0, 34.0, 13.0);

        // Perform the method call
        AnalysisDTO result = analysisService.getDailyAnalysis(userDTO);

        // Assertion
        assertEquals(expected, result);
    }

    private UserFood createMockUserFood(int weight, int calorie, double protein, double fat, double salt) {
        UserFood mockUserFood = mock(UserFood.class);
        Food mockFood = mock(Food.class);

        when(mockFood.getCalorie()).thenReturn(calorie);
        when(mockFood.getProtein()).thenReturn(protein);
        when(mockFood.getFat()).thenReturn(fat);
        when(mockFood.getSalt()).thenReturn(salt);

        when(mockUserFood.getFood()).thenReturn(mockFood);
        when(mockUserFood.getWeight()).thenReturn((double) weight);

        return mockUserFood;
    }

}
