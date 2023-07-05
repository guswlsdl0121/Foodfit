package Foodfit.BackEnd.food;


import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Repository.FoodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;


    @Test
    @DisplayName("foodRepository 초기화 확인")
    void t1() throws Exception {
        //then
        assertThat(foodRepository).isNotNull();
    }

    @Test
    @DisplayName("한글로 최대 10개의 음식을 검색할 수 있다.")
    void t2() throws Exception {
        //given
        List<Food> dummyData = createDummyData();
        foodRepository.saveAll(dummyData);

        //when
        List<Food> findFoods = foodRepository.findTop10ByNameContainingIgnoreCase("사과");

        //then
        assertThat(findFoods).hasSize(10)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        "사과", "사과 파이","사과 주스",
                        "사과잼","사과 칩","사과 샐러드",
                        "사과 빵","사과 요거트","사과 컵케이크",
                        "사과 스무디");
    }

    @Test
    @DisplayName("검색 결과가 없을 경우 빈 리스트가 넘어온다.")
    void t3() throws Exception {
        //given
        List<Food> dummyData = createDummyData();
        foodRepository.saveAll(dummyData);
        //when
        List<Food> findFoods = foodRepository.findTop10ByNameContainingIgnoreCase("포도");
        //then
        assertThat(findFoods).isEmpty();
    }

    private List<Food> createDummyData(){
        Food food1 = Food.builder()
                .name("사과")
                .calorie(52)
                .protein(0.26)
                .fat(0.17)
                .salt(0.01)
                .build();

        Food food2 = Food.builder()
                .name("사과 파이")
                .calorie(320)
                .protein(0.5)
                .fat(19)
                .salt(0.25)
                .build();

        Food food3 = Food.builder()
                .name("사과 주스")
                .calorie(46)
                .protein(0.1)
                .fat(0.1)
                .salt(0.01)
                .build();

        Food food4 = Food.builder()
                .name("사과잼")
                .calorie(50)
                .protein(0.1)
                .fat(0.1)
                .salt(0.02)
                .build();

        Food food5 = Food.builder()
                .name("사과 칩")
                .calorie(150)
                .protein(1)
                .fat(10)
                .salt(0.05)
                .build();

        Food food6 = Food.builder()
                .name("사과 샐러드")
                .calorie(80)
                .protein(0.5)
                .fat(0.5)
                .salt(0.02)
                .build();

        Food food7 = Food.builder()
                .name("사과 빵")
                .calorie(260)
                .protein(5)
                .fat(6)
                .salt(0.4)
                .build();

        Food food8 = Food.builder()
                .name("사과 요거트")
                .calorie(100)
                .protein(4)
                .fat(3)
                .salt(0.07)
                .build();

        Food food9 = Food.builder()
                .name("사과 컵케이크")
                .calorie(235)
                .protein(2)
                .fat(11)
                .salt(0.2)
                .build();

        Food food10 = Food.builder()
                .name("사과 스무디")
                .calorie(130)
                .protein(1)
                .fat(0.3)
                .salt(0.02)
                .build();

        Food food11 = Food.builder()
                .name("사과 케이크")
                .calorie(320)
                .protein(4)
                .fat(14)
                .salt(0.3)
                .build();

        Food food12 = Food.builder()
                .name("바나나")
                .calorie(96)
                .protein(1.2)
                .fat(0.3)
                .salt(0.01)
                .build();

        Food food13 = Food.builder()
                .name("바나나 빵")
                .calorie(196)
                .protein(3)
                .fat(6)
                .salt(0.17)
                .build();

        Food food14 = Food.builder()
                .name("바나나 스무디")
                .calorie(150)
                .protein(2)
                .fat(1)
                .salt(0.07)
                .build();

        Food food15 = Food.builder()
                .name("바나나 칩")
                .calorie(150)
                .protein(1)
                .fat(9)
                .salt(0.02)
                .build();


        return List.of(food1, food2, food3, food4, food5, food6, food7, food8, food9, food10,
                food11,food12,food13,food14,food15);
    }
}
