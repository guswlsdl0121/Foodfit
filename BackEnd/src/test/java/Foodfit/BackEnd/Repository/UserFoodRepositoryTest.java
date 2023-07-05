package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Domain.UserFood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;



@DataJpaTest
@ActiveProfiles("test")
class UserFoodRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFoodRepository userFoodRepository;

    @Autowired
    private FoodRepository foodRepository;

    private User dummyUser;
    private Food dummyFood;


    @BeforeEach
    void setUp() {
        dummyUser = User.builder()
                .name("테스트 유저1")
                .age(22)
                .gender(Gender.MALE)
                .uid(21024010L)
                .build();

        dummyFood = Food.builder()
                .name("사과")
                .calorie(52)
                .protein(0.26)
                .fat(0.17)
                .salt(0.01)
                .build();

        userRepository.save(dummyUser);
        foodRepository.save(dummyFood);
    }

    @Test
    @DisplayName("UserFood를 유저정보(객체)와 기간으로 검색할 수 있다.")
    void t1() throws Exception {
        //given
        List<UserFood> userFoods = createUserFood();
        userFoodRepository.saveAll(userFoods);

        LocalDateTime startDateTime = LocalDateTime.of(2023, 7, 5, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 7, 5, 23, 59);


        //when
        List<UserFood> actualUserFoods = userFoodRepository.findByUserAndDateBetween(dummyUser, startDateTime, endDateTime);

        //then
        assertThat(actualUserFoods).hasSize(2)
                .containsExactly(userFoods.get(0), userFoods.get(1));
    }


    /*
    * author : minturtle
    * description : 0, 1번은 23년 7월 5일, 2번은 23년 7월 6일로 설정된 userFood 리스트를 리턴한다.
    * */
    public List<UserFood> createUserFood(){
        UserFood userFood1 = UserFood.builder()
                .food(dummyFood)
                .user(dummyUser)
                .weight(120.0)
                .date(LocalDateTime.of(2023, 7, 5, 0, 0))
                .build();

        UserFood userFood2 = UserFood.builder()
                .food(dummyFood)
                .user(dummyUser)
                .weight(120.0)
                .date(LocalDateTime.of(2023, 7, 5, 23, 59))
                .build();


        UserFood userFood3 = UserFood.builder()
                .food(dummyFood)
                .user(dummyUser)
                .weight(120.0)
                .date(LocalDateTime.of(2023, 7, 6, 0,0))
                .build();

        return List.of(userFood1, userFood2, userFood3);
    }
}