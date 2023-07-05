package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.DTO.AnalysisDTO;
import Foodfit.BackEnd.DTO.Response.BoardDTO;
import Foodfit.BackEnd.DTO.TotalNutrient;
import Foodfit.BackEnd.Domain.Board;
import Foodfit.BackEnd.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static Foodfit.BackEnd.Exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardListService {
    private final BoardRepository boardRepository;
    /*
     * author: guswls
     * description: boardList를 반환하는 메서드
     */
    public List<BoardDTO> getBoardList(Long userId) {
        List<Board> boards = boardRepository.findAll();
        return convertToDtoList(boards, userId);
    }

    /**
     * board들을 DTO들의 List로 변환
     */
    private List<BoardDTO> convertToDtoList(List<Board> boards, Long userId) {
        return boards.stream()
                .map(board -> convertToDto(board, userId))
                .collect(Collectors.toList());
    }

    /**
     * board를 DTO로 변환
     */
    private BoardDTO convertToDto(Board board, Long userId) {
        boolean isLike = false;
        if (userId != null) {
            isLike = board.isLikedByUser(userId);
        }
        List<String> tags = board.getTags();
        return BoardDTO.toDTO(board, isLike, tags);
    }

    /*
     * author: guswls
     * description: 게시글의 태그를 토대로 영양분 가져오기
     */
    public AnalysisDTO getNutrientInfo(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoBoardException::new);

        TotalNutrient totalNutrient = board.calculateTotalNutrients();

        int totalCalorie = totalNutrient.totalCalorie();
        double totalProtein = totalNutrient.totalProtein();
        double totalFat = totalNutrient.totalFat();
        double totalSalt = totalNutrient.totalSalt();

        return new AnalysisDTO(totalCalorie, totalProtein, totalFat, totalSalt);
    }
}
