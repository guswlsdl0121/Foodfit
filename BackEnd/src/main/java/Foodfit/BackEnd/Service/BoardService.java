package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.Response.BoardDTO;
import Foodfit.BackEnd.DTO.Response.BoardListResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {
    private final BoardFoodRepository boardFoodRepository;
    private final BoardImageRepository boardImageRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

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

    public void deleteBoard(Long boardId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoUserException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(NoBoardException::new);

        // 게시글의 유저와 요청 유저가 다르면 에러 발생
        if (!board.getUser().equals(user)) {
            throw new UnAuthorizedException(AuthorizeExceptionMessages.CANNOT_MATCH_USER.MESSAGE);
        }
        boardRepository.delete(board);
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
