package Foodfit.BackEnd.DTO.Response;


import Foodfit.BackEnd.Domain.Board;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public record BoardDTO(
        byte[] profileImage,
        String author,
        String description,
        List<String> images,
        boolean isLike,
        int likeCount,
        List<String> tags
) {
    public static BoardDTO toDTO(Board board, boolean isLikedByUser, List<String> tags){
        return new BoardDTO(
                board.getAuthorProfileImage(),
                board.getAuthorName(),
                board.getContent(),
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