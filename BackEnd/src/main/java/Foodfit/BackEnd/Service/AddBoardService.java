package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Domain.*;
import Foodfit.BackEnd.Exception.AuthorizeExceptionMessages;
import Foodfit.BackEnd.Exception.NotFoundException.NoUserException;
import Foodfit.BackEnd.Exception.NotFoundException.NoBoardException;
import Foodfit.BackEnd.Exception.UnAuthorizedException;
import Foodfit.BackEnd.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddBoardService {
    private final BoardFoodRepository boardFoodRepository;
    private final BoardImageRepository boardImageRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public void createBoard(String content, List<MultipartFile> images, List<Long> foodIds, Long userId) throws IOException {
        LocalDateTime date = LocalDateTime.now();
        User user = userRepository.findById(userId).orElseThrow(NoUserException::new);
        Board board = Board.builder()
                .content(content)
                .date(date)
                .user(user)
                .build();

        List<BoardImage> boardImages = createBoardImageList(images, board);
        List<BoardFood> boardFoods = createBoardFoodList(foodIds, board);

        boardRepository.save(board);
        boardImageRepository.saveAll(boardImages);
        boardFoodRepository.saveAll(boardFoods);

    }

    @Transactional
    public void updateBoard(Long boardId, String content, List<MultipartFile> images, List<Long> foodIds, Long userId) throws IOException{
        LocalDateTime date = LocalDateTime.now();
        User user = userRepository.findById(userId).orElseThrow(NoUserException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(NoBoardException::new);

        // 게시글의 유저와 요청 유저가 다르면 에러 발생
        if (!board.getUser().equals(user)) {
            throw new UnAuthorizedException(AuthorizeExceptionMessages.CANNOT_MATCH_USER.MESSAGE);
        }

        board = Board.builder()
                .id(board.getId())
                .content(content)
                .date(date)
                .user(user)
                .build();

        List<BoardImage> boardImages = createBoardImageList(images, board);
        boardImageRepository.deleteAll();
        boardImageRepository.saveAll(boardImages);

        List<BoardFood> boardFoods = createBoardFoodList(foodIds, board);
        boardFoodRepository.deleteAll();
        boardFoodRepository.saveAll(boardFoods);

        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoUserException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(NoBoardException::new);

        // 게시글의 유저와 요청 유저가 다르면 에러 발생
        if (!board.getUser().equals(user)) {
            throw new UnAuthorizedException(AuthorizeExceptionMessages.CANNOT_MATCH_USER.MESSAGE);
        }
        boardRepository.delete(board);
    }


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

    /*
     * author : junha
     * description : 업로드한 이미지를 boardImage List로 생성하는 메서드
     * */
    private List<BoardImage> createBoardImageList(List<MultipartFile> images, Board board) throws IOException {
        List<BoardImage> boardImages = new ArrayList<>();

        for (MultipartFile image : images) {
            byte [] byteImage = image.getBytes();
            BoardImage boardImage = BoardImage.builder()
                    .imageBlob(byteImage)
                    .board(board)
                    .build();

            boardImages.add(boardImage);
        }

        return boardImages;
    }

    /*
     * author : junha
     * description : 업로드한 이미지를 boardFood List로 생성하는 메서드
     * */
    private List<BoardFood> createBoardFoodList(List<Long> foodIds, Board board){
        List<BoardFood> boardFoods = new ArrayList<>();
        List<Food> searchFoods = foodRepository.findAllByIdIn(foodIds);
        int foodIdsSize = foodIds.size();

        for(int i = 0; i<foodIdsSize; i++){
            Food food = searchFoods.get(i);
            BoardFood boardFood = BoardFood.builder()
                    .board(board)
                    .food(food)
                    .build();

            boardFoods.add(boardFood);
        }

        return boardFoods;
    }




}
