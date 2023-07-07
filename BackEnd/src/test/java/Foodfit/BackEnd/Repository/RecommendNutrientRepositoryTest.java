package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.RecommendNutrient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RecommendNutrientRepositoryTest {


    @Autowired
    private RecommendNutrientRepository recommendNutrientRepository;

    @Test
    @DisplayName("나이, 성별, 영양소로 해당 영양소의 권장 섭취량을 검색할 수 있다.")
    void t1() throws Exception {
        //given
        saveRecommendNutrients();
        //when
        final Optional<RecommendNutrient> actual = recommendNutrientRepository.findRecommendNutrient(22, Gender.MALE, "fat");
        //then

        assertThat(actual.get()).extracting("nutrientType", "gender", "startAge", "endAge")
                .containsExactly("fat", Gender.MALE, 19, 29);


    }



    public void saveRecommendNutrients() {
        recommendNutrientRepository.save(new RecommendNutrient( "calorie", Gender.MALE, 15, 18, 2700.0));
        recommendNutrientRepository.save(new RecommendNutrient( "calorie", Gender.FEMALE, 15, 18, 2000.0));
        recommendNutrientRepository.save(new RecommendNutrient( "protein", Gender.MALE, 15, 18, 55.0));
        recommendNutrientRepository.save(new RecommendNutrient( "protein", Gender.FEMALE, 15, 18, 45.0));
        recommendNutrientRepository.save(new RecommendNutrient( "protein", Gender.MALE, 19, 29, 55.0));
        recommendNutrientRepository.save(new RecommendNutrient( "fat", Gender.FEMALE, 15, 18, 55.0));
        recommendNutrientRepository.save(new RecommendNutrient( "fat", Gender.MALE, 19, 29, 72.0));
        recommendNutrientRepository.save(new RecommendNutrient( "fat", Gender.FEMALE, 19, 29, 58.0));
        recommendNutrientRepository.save(new RecommendNutrient( "fat", Gender.MALE, 30, 49, 66.0));
        recommendNutrientRepository.save(new RecommendNutrient( "fat", Gender.FEMALE, 30, 49, 52.0));
    }

}