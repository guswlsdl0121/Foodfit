package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Like;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {


    Optional<Like> findLikeByBoard_IdAndUser_Id(Long boardId, Long userId);


    Long countLikeByBoard_Id(Long boardId);
}
