package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Board;
import Foodfit.BackEnd.Domain.BoardFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFoodRepository extends JpaRepository<BoardFood, Long> {
    void deleteByBoard(Board board);
}
