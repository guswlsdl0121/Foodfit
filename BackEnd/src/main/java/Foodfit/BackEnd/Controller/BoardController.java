package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.DTO.Request.AddBoardDTO;
import Foodfit.BackEnd.DTO.Request.UserLikeRequest;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Service.AddBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게시판 API", description = "게시글 추가, 조회, 삭제, 좋아요를 담당합니다.")
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final AddBoardService addBoardService;

    @PostMapping
    @AdditionalUserInfoCheck
    public ResponseEntity<AddBoardDTO> addBoard(@RequestBody AddBoardDTO addBoardDTO, HttpServletRequest request){
        Long userId = ((UserDTO) request.getAttribute("user")).getId();
        List<byte[]> images = addBoardDTO.toImageList();
        List<Long> foodsIds = addBoardDTO.toFoodIdList();

//        addBoardService.addBoard();


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(description = "유저가 좋아요를 누르면, likeClicked 에 true를, 좋아요를 취소하면 false를 넣어서 보내주시면 됩니다.")
    @PostMapping("like")
    @LoginCheck
    public ResponseEntity userLike(HttpServletRequest req, @RequestBody UserLikeRequest reqBody){
        UserDTO user = (UserDTO) req.getAttribute("user");

        addBoardService.updateLike(reqBody.getBoardId(),user.getId() ,reqBody.isLikeClicked());
        return new ResponseEntity(HttpStatus.OK);
    }

}
