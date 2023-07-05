package Foodfit.BackEnd.DTO.Response;

import Foodfit.BackEnd.Domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public record BoardDTO(
        byte[] profileImage,
        String author,
        String description,
        List<byte[]> images,
        boolean isLike,
        int likeCount,
        List<String> tags
) {
    public static BoardDTO toDTO(Board board, boolean isLikedByUser, List<String> tags) {
        return new BoardDTO(
                board.getAuthorProfileImage(),
                board.getAuthorName(),
                board.getContent(),
                board.getImages(),
                isLikedByUser,
                board.getLikeCount(),
                tags
        );
    }
}
