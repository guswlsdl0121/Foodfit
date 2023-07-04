package Foodfit.BackEnd.Service;


import Foodfit.BackEnd.Domain.Board;
import Foodfit.BackEnd.Domain.Like;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Exception.AuthorizeExceptionMessages;
import Foodfit.BackEnd.Exception.UnAuthorizedException;
import Foodfit.BackEnd.Repository.BoardRepository;
import Foodfit.BackEnd.Repository.LikeRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


//TODO : Migrate to BoardService
@Service
@RequiredArgsConstructor
public class MinseokBoardService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void updateLike(Long boardId, Long userId, boolean userLike) {
        final Like like = findUserLiked(boardId, userId);
        // 만약 유저가 이미 좋아요를 눌렀고, 유저가 like를 취소하기를 원한다면 like 객체를 삭제
        if((like != null) && !userLike){
            likeRepository.delete(like);
            return;
        }
        // 만약 유저가 좋아요를 누르지 않았고, 유저가 like를 누르기를 원한다면 like 객체를 추가
        if(like == null && userLike){
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UnAuthorizedException(AuthorizeExceptionMessages.CANNOT_FIND_USER_FROM_TOKEN.MESSAGE));
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new IllegalArgumentException("Board를 찾을 수 없습니다."));

            Like newLike = Like.builder()
                    .user(user)
                    .board(board)
                    .build();

            likeRepository.save(newLike);
        }
    }

    /*
    * author : minturtle
    * description : 사용자가 Board에 대해 좋아요를 눌렀는지 알 수 있는 메서드
    * */
    public Like findUserLiked(Long boardId, Long userId){
        Optional<Like> findLike = likeRepository.findLikeByBoard_IdAndUser_Id(boardId, userId);

        return findLike.orElse(null);
    }
}
