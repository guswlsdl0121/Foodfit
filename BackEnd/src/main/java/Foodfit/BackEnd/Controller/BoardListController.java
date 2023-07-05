package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.DTO.AnalysisDTO;
import Foodfit.BackEnd.DTO.Response.BoardDTO;
import Foodfit.BackEnd.DTO.Response.BoardListResponse;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Service.BoardListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "게시판 조회API", description = "게시글  조회를 담당합니다.")
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardListController {
    private final BoardListService boardListService;

    @GetMapping
    @LoginCheck
    public BoardListResponse getAllBoards(HttpServletRequest req) {
        Long userId = ((UserDTO) req.getAttribute("user")).getId();
        List<BoardDTO> boardList = boardListService.getBoardList(userId);
        return new BoardListResponse(boardList);
    }

    @GetMapping("/{boardId}/nutrient")
    public ResponseEntity<AnalysisDTO> getBoardNutrient(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardListService.getNutrientInfo(boardId));
    }

    // 로그인된 사용자와 로그인되지 않은 사용자에 대한 분리
    @ExceptionHandler(NoSuchElementException.class)
    public BoardListResponse handleNoSuchElementException(NoSuchElementException e) {
        List<BoardDTO> boardList = boardListService.getBoardList(null);
        return new BoardListResponse(boardList);
    }
}
