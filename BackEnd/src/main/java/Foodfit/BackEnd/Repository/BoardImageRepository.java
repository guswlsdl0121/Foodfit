package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Board;
import Foodfit.BackEnd.Domain.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findByBoardId(Long id);

    void deleteByBoard(Board board);
}
