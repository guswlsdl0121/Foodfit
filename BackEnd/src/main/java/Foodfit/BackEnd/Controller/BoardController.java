package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.DTO.Request.AddBoardDTO;
import Foodfit.BackEnd.DTO.Request.DeleteBoardRequest;
import Foodfit.BackEnd.DTO.Request.UpdateBoardRequest;
import Foodfit.BackEnd.DTO.Request.UserLikeRequest;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Service.BoardLikeService;
import Foodfit.BackEnd.Service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "게시판 API", description = "게시글 추가, 조회, 삭제, 좋아요를 담당합니다.")
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    @Operation(summary = "게시글 작성", description="로그인이 되어 있어야 합니다.\n" +
            "내용, 이미지, 태그를 보내주어야 합니다.")
    @PostMapping
    @LoginCheck
    public ResponseEntity<Void> createBoard(@ModelAttribute AddBoardDTO addBoardDTO, HttpServletRequest request) throws IOException {
        Long userId = ((UserDTO) request.getAttribute("user")).getId();
        String content = addBoardDTO.getContent();
        List<MultipartFile> images = addBoardDTO.toImageList();
        List<Long> foodIds = addBoardDTO.toFoodIdList();

        boardService.createBoard(content, images, foodIds, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "게시글 수정", description="로그인이 되어 있어야 합니다.\n" +
            "게시글 Id, 내용, 이미지, 태그를 보내주어야 합니다.")
    @PutMapping
    @LoginCheck
    public ResponseEntity<Void> updateBoard(@ModelAttribute UpdateBoardRequest reqBody, HttpServletRequest request) throws IOException {
        Long userId = ((UserDTO) request.getAttribute("user")).getId();
        Long boardId = reqBody.getBoardId();
        String content = reqBody.getContent();
        List<MultipartFile> images = reqBody.toImageList();
        List<Long> foodIds = reqBody.toFoodIdList();

        boardService.updateBoard(boardId, content, images, foodIds, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "게시글 삭제", description="로그인이 되어 있어야 합니다.\n" +
            "게시글 Id를 보내주어야 합니다.")
    @DeleteMapping
    @LoginCheck
    public ResponseEntity<Void> deleteBoard(@RequestBody DeleteBoardRequest reqBody, HttpServletRequest request) {
        Long userId = ((UserDTO) request.getAttribute("user")).getId();

        boardService.deleteBoard(reqBody.getBoardId(), userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(description = "유저가 좋아요를 누르면, likeClicked 에 true를, 좋아요를 취소하면 false를 넣어서 보내주시면 됩니다.")
    @PostMapping("like")
    @LoginCheck
    public ResponseEntity userLike(HttpServletRequest req, @RequestBody UserLikeRequest reqBody){
        UserDTO user = (UserDTO) req.getAttribute("user");

        boardLikeService.updateLike(reqBody.getBoardId(),user.getId() ,reqBody.isLikeClicked());
        return new ResponseEntity(HttpStatus.OK);
    }
}
