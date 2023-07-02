package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findTop10ByNameContainingIgnoreCase(String name);
    //TODO [HJ] 성능 비교를 위한 페이지네이션  메서드
    Page<Food> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Food> findAllByIdIn(List<Long> ids);
}

