package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.DTO.Request.DeleteBoardRequest;
import Foodfit.BackEnd.DTO.Request.UserLikeRequest;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Service.BoardLikeService;
import Foodfit.BackEnd.Service.BoardService;
import io.jsonwebtoken.lang.Collections;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @LoginCheck
    public ResponseEntity<Void> createBoard(@RequestParam String content,
                                            @RequestParam(value = "images", required = false) MultipartFile[] images,
                                            @RequestParam(required = false) Long[] tags,
                                            HttpServletRequest request) throws Exception {
        Long userId = ((UserDTO) request.getAttribute("user")).getId();

        List<MultipartFile> imageList = Collections.arrayToList(images);
        List<Long> foodIds = Collections.arrayToList(tags);

        boardService.createBoard(content, imageList, foodIds, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private List<MultipartFile> toImageList(List<MultipartFile> images){return new ArrayList<>(new ArrayList<>(images));
    }

    @Operation(summary = "게시글 수정", description="로그인이 되어 있어야 합니다.\n" +
            "게시글 Id, 내용, 이미지, 태그를 보내주어야 합니다.")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @LoginCheck
    public ResponseEntity<Void> updateBoard(@RequestParam Long boardId,
                                            @RequestParam String content,
                                            @RequestParam(value = "images", required = false) MultipartFile[] images,
                                            @RequestParam(required = false) Long[] tags,
                                            HttpServletRequest request) throws Exception {
        Long userId = ((UserDTO) request.getAttribute("user")).getId();;
        List<MultipartFile> imageList =  Collections.arrayToList(images);
        List<Long> foodIds = Collections.arrayToList(tags);

        boardService.updateBoard(boardId, content, imageList, foodIds, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "게시글 삭제", description="로그인이 되어 있어야 합니다.\n" +
            "게시글 Id를 보내주어야 합니다.")
    @DeleteMapping
    @LoginCheck
    public ResponseEntity<Void> deleteBoard(@RequestParam(value="boardId") Long boardId, HttpServletRequest request) {
        Long userId = ((UserDTO) request.getAttribute("user")).getId();

        boardService.deleteBoard(boardId, userId);

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
