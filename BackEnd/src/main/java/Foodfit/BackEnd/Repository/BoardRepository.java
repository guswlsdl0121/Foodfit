package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
