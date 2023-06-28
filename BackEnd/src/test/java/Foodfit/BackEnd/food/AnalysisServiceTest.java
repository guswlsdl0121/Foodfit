package Foodfit.BackEnd.food;

import Foodfit.BackEnd.DTO.PeriodAnalysisDTO;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import Foodfit.BackEnd.Repository.UserFoodRepository;
import Foodfit.BackEnd.Service.AnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AnalysisServiceTest {

    private AnalysisService analysisService;

    @Mock
    private UserFoodRepository userFoodRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        analysisService = new AnalysisService(userFoodRepository);
    }

    @Test
    void testGetPeriodAnalysis() {
        // Mock data
        User user = new User();
        LocalDate startDate = LocalDate.of(2023, 6, 26);
        LocalDate endDate = LocalDate.of(2023, 6, 30);
        String nutrient = "protein";

        // Mock UserFood objects
        List<UserFood> userFoods = new ArrayList<>();
        UserFood mockUserFood1 = createMockUserFood(200, 10.0, 5.0, 2.0);
        userFoods.add(mockUserFood1);

        UserFood mockUserFood2 = createMockUserFood(300, 15.0, 8.0, 3.0);
        userFoods.add(mockUserFood2);

        when(userFoodRepository.findByUserAndDateBetween(user, startDate.atStartOfDay(), endDate.atTime(23, 59, 59)))
                .thenReturn(userFoods);

        // Expected result
        List<PeriodAnalysisDTO> expected = new ArrayList<>();
        expected.add(new PeriodAnalysisDTO(LocalDate.of(2023, 6, 26), 8.0));
        expected.add(new PeriodAnalysisDTO(LocalDate.of(2023, 6, 27), 12.0));
        expected.add(new PeriodAnalysisDTO(LocalDate.of(2023, 6, 28), 16.0));
        expected.add(new PeriodAnalysisDTO(LocalDate.of(2023, 6, 29), 20.0));
        expected.add(new PeriodAnalysisDTO(LocalDate.of(2023, 6, 30), 24.0));

        // Perform the method call
        List<PeriodAnalysisDTO> result = analysisService.getPeriodAnalysis(user, startDate, endDate, nutrient);

        // Assertion
        assertEquals(expected, result);
    }

    private UserFood createMockUserFood(int weight, double protein, double fat, double salt) {
        UserFood mockUserFood = mock(UserFood.class);
        Food mockFood = mock(Food.class);

        when(mockFood.getProtein()).thenReturn(protein);
        when(mockFood.getFat()).thenReturn(fat);
        when(mockFood.getSalt()).thenReturn(salt);

        when(mockUserFood.getFood()).thenReturn(mockFood);
        when(mockUserFood.getWeight()).thenReturn((double) weight);

        return mockUserFood;
    }

}
