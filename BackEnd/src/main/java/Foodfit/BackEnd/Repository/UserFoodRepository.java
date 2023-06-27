package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Domain.UserFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFoodRepository extends JpaRepository<UserFood, Long> {
}
