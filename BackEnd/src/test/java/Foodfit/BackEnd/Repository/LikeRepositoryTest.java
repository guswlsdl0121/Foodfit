package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.Board;
import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.Like;
import Foodfit.BackEnd.Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;



@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    private User dummyUser;
    private User dummyUser2;
    private User dummyUser3;


    private Board dummyBoard;

    @BeforeEach
    void setUp() {
        /*
        * 더미 데이터 : 유저 1,2,3 , 게시글1
        * 게시글은 유저 1이 작성하였고, 게시글에 좋아요는 유저 1,2가 눌렀다.
        * */

        initializeDummyUserAndBoard();
        // 데이터 무결성 해결을 위한 save
        boardRepository.save(dummyBoard);
        userRepository.saveAll(List.of(dummyUser, dummyUser2, dummyUser3));

        List<Like> dummyLikes = initiallizeDummyLikes();
        likeRepository.saveAll(dummyLikes);
    }

    @Test
    @DisplayName("like repository 주입 여부 확인")
    void t1() throws Exception {
        //then
        assertThat(likeRepository).isNotNull();
        assertThat(boardRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    @DisplayName("board id와 user id로 좋아요를 눌렀는지 확인할 수 있다. 좋아요를 눌렀다면 like 객체가 리턴된다.")
    void t2() throws Exception {
        //given
        //when
        Like like = likeRepository.findLikeByBoard_IdAndUser_Id(dummyBoard.getId(), dummyUser.getId())
                .orElseThrow(RuntimeException::new);
        //then
        assertThat(like)
                .hasFieldOrPropertyWithValue("user", dummyUser)
                .hasFieldOrPropertyWithValue("board", dummyBoard);

    }


    @Test
    @DisplayName("board id와 user id로 좋아요를 눌렀는지 확인할 수 있다. 좋아요를 눌렀다면 Optional.empty가 리턴된다.")
    void t3() throws Exception {
        //given
        //when
        Optional<Like> optionalLike = likeRepository
                .findLikeByBoard_IdAndUser_Id(dummyBoard.getId(), dummyUser3.getId());
        //then
        assertThat(optionalLike.isEmpty()).isTrue();
    }


    private void initializeDummyUserAndBoard(){
       dummyUser = User.builder()
                .name("테스트 유저1")
                .age(22)
                .gender(Gender.MALE)
                .uid(21024010L)
                .build();

       dummyUser2 = User.builder()
                .name("테스트 유저2")
                .age(23)
                .gender(Gender.MALE)
                .uid(21124010L)
                .build();

       dummyUser3 = User.builder()
                .name("테스트 유저3")
                .age(19)
                .gender(Gender.MALE)
                .uid(21023010L)
                .build();

       dummyBoard = Board.builder()
                .content("테스트용 Board 입니다.")
                .user(dummyUser)
                .build();

    }


    private List<Like> initiallizeDummyLikes(){
        Like like1 = Like.builder()
                .board(dummyBoard)
                .user(dummyUser)
                .build();

        final Like like2 = Like.builder()
                .board(dummyBoard)
                .user(dummyUser2)
                .build();
        return List.of(like1, like2);
    }

}