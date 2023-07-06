package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Domain.*;
import Foodfit.BackEnd.Repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardImageRepository boardImageRepository;

    @Mock
    private BoardFoodRepository boardFoodRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikeRepository likeRepository;



    @Test
    void InjectionTest() {
        assertNotNull(foodRepository);
        assertNotNull(boardRepository);
        assertNotNull(boardImageRepository);
        assertNotNull(boardFoodRepository);
        assertNotNull(userRepository);
        assertNotNull(likeRepository);
        assertNotNull(boardService);
    }

//    @Test
//    void createBoard() {
//        // when
//        Long userId = 1L; // 사용자 ID
//        User user = new User(userId, "testuser", 25, Gender.MALE, 1234L, null);
//
//        String content = "Test boardList content";
//        List<MultipartFile> images = new ArrayList<>(); // 이미지 목록 생성
//        List<Long> foodIds = Arrays.asList(1L, 2L, 3L); // 음식 ID 목록 생성
//        Food food = foodRepository.findById(1L).get();
//
//        Board board = Board.builder()
//                .content(content)
//                .date(LocalDateTime.now())
//                .user(user)
//                .build();
//
//        BoardImage boardImage = BoardImage.builder()
//                .board(board)
//                .build();
//
//        BoardFood boardFood = BoardFood.builder()
//                .board(board)
//                .food(food)
//                .build();
//
//
//        when(boardRepository.findById(userId)).thenReturn(Optional.of(board));
//        when(boardImageRepository.findById(board.getId())).thenReturn(Optional.of(boardImage));
//        when(boardFoodRepository.findById(board.getId())).thenReturn(Optional.of(boardFood));
//
//        assertDoesNotThrow(() -> boardService.createBoard(content, images, foodIds, userId));
//
//
//
//
//
//
//
//    }

    @Test
    void updateBoard() {
    }

    @Test
    void deleteBoard() {
    }
}