package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddBoardService {
    private final BoardRepository boardRepository;

//    @Transactional
//    public void addBoard()
}
