package Foodfit.BackEnd.DTO.Response;


import Foodfit.BackEnd.Domain.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public record BoardDTO(
        Long id,
        byte[] profileImage,
        String author,
        String description,
        LocalDateTime date,
        List<String> images,
        boolean isLike,
        int likeCount,
        List<String> tags
) {
    public static BoardDTO toDTO(Board board, boolean isLikedByUser, List<String> tags){
        return new BoardDTO(
                board.getId(),
                board.getAuthorProfileImage(),
                board.getAuthorName(),
                board.getContent(),
                board.getDate(),
                byteToUrl(board.getImages()),
                isLikedByUser,
                board.getLikeCount(),
                tags
        );
    }

    private static List<String> byteToUrl(List<byte[]> images){
        List<String> urls = new ArrayList<>();

        for(byte[] image : images){
            String base64Encode = Base64.getEncoder().encodeToString(image);
            base64Encode = "data:image/png;base64," + base64Encode;
            urls.add(base64Encode);
        }
        return urls;
    }

}