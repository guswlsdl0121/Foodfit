package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static Foodfit.BackEnd.Exception.NotFoundException.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "boards")
@Builder
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    //like를 조회하기 위한 양방향 mapping 
    @OneToMany(mappedBy = "board")
    private List<Like> likes;

    //태그를 조회하기 위한 양방향 mapping
    @OneToMany(mappedBy = "board")
    private List<BoardFood> boardFoods;

    //게시글 사진들을 조회하기 위한 양방향 mapping
    @OneToMany(mappedBy = "board")
    private List<BoardImage> boardImages;

    /**
     * 게시글 좋아요 개수 반환 메서드
     * @return int
     */
    public int getLikeCount() {
        return likes.size();
    }

    /**
     * 게시글 테그 리스트 반환
     * @return List<String>
     */
    public List<String> getTags() {
        return boardFoods.stream()
                .map(boardFood -> boardFood.getFood().getName())
                .collect(Collectors.toList());
    }

    /**
     * 게시글 작성자이름 반환
     * @return String
     */
    public String getAuthorName() {
        return user.getName();
    }

    /**
     * 작성자 프로필 이미지 반환
     * @return byte[]
     */
    public byte[] getAuthorProfileImage() {
        return user.getProfileImage().getBytes();
    }

    /**
     * 게시글 이미지 리스트 반환
     * @return List<byte[]>
     */
    public List<byte[]> getImages() {
        return boardImages.stream()
                .map(BoardImage::getImageBlob)
                .collect(Collectors.toList());
    }

    /**
     * 사용자의 게시글 좋아요 여부 반환
     * @return boolean
     */
    public boolean isLikedByUser(Long userId) {
        return likes.stream()
                .anyMatch(like -> like.getUser().getId().equals(userId));
    }

    public List<? extends Number> calculateTotalNutrients() {
        int totalCalorie = 0;
        double totalProtein = 0.0;
        double totalFat = 0.0;
        double totalSalt = 0.0;

        for (BoardFood boardFood : boardFoods) {
            Food food = boardFood.getFood();
            if (food == null) throw new NoFoodException();

            totalCalorie += food.getCalorie();
            totalProtein += food.getProtein();
            totalFat += food.getFat();
            totalSalt += food.getSalt();
        }
        return List.of(totalCalorie, totalProtein, totalFat, totalSalt);
    }
}
