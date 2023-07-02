package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Aop.Annotations.TimeLog;
import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Exception.NotFoundException.NoFoodException;
import Foodfit.BackEnd.Repository.FoodRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static Foodfit.BackEnd.Exception.BadRequestException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodSearchService {
    private final FoodRepository foodRepository;

    /**
     * methodName : searchFoods
     * author : guswlsdl
     * description : 검색어에 대한 상위 10개 항목의 검색 결과를 가져온다.
     * @param  name
     * @return foodPage.getContent();
     */
    @TimeLog
    public List<Food> searchFoods(String name) {
        List<Food> foods = foodRepository.findTop10ByNameContainingIgnoreCase(name);

        //예외 처리
        if (containsEnglish(name)) throw new EnglishSearchException();
        if (foods.isEmpty()) throw new NoFoodException();

        return foods;
    }

    //TODO [HJ] 성능 비교를 위핸 Pagination을 이용한 코드
    public List<Food> searchFoodsWithPagination(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foodPage = foodRepository.findAllByNameContainingIgnoreCase(name, pageable);

        if (containsEnglish(name)) throw new EnglishSearchException();
        if (foodPage.isEmpty()) throw new NoFoodException();

        return foodPage.getContent();
    }

    /**
     * methodName : containsEnglish
     * author : guswlsdl
     * description : 검색어에 영어가 포함되어있는지 확인한다.
     * @param  name
     * @return boolean
     */
    private boolean containsEnglish(String name) {
        for (char c : name.toCharArray()) {
            if (Character.isLetter(c) && Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
                return true;
            }
        }
        return false;
    }

}
