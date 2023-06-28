package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.UserFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserFoodRepository extends JpaRepository<UserFood, Long> {
    List<UserFood> findAllByUserIdAndDateBetween(Long user_id, LocalDateTime todayStart, LocalDateTime todayEnd);
}
