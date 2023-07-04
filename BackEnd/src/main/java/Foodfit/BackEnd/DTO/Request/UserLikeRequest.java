package Foodfit.BackEnd.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLikeRequest {
    Long boardId;
    boolean likeClicked;
}
