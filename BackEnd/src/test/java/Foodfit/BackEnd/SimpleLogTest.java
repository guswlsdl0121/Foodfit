package Foodfit.BackEnd;

import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Transactional
@Commit
class SimpleLogTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUserTest() {
        // 사용자 생성
        User user = new User();
        user.setAge(25);
        user.setGender(Gender.MALE);

        // 사용자 저장
        User savedUser = userRepository.save(user);

        // 저장된 사용자 검증
        Assertions.assertNotNull(savedUser.getId(), "Saved user ID should not be null");

        // 저장된 사용자 조회
        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        Assertions.assertNotNull(retrievedUser, "Retrieved user should not be null");
        Assertions.assertEquals(savedUser.getId(), retrievedUser.getId(), "Retrieved user ID should match");
        Assertions.assertEquals(user.getAge(), retrievedUser.getAge(), "Retrieved user age should match");
        Assertions.assertEquals(user.getGender(), retrievedUser.getGender(), "Retrieved user gender should match");
    }

    @Test
    void find() {
    }
}