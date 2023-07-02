package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findTop10ByNameContainingIgnoreCase(String name);
    List<Food> findAllByIdIn(List<Long> ids);
}

