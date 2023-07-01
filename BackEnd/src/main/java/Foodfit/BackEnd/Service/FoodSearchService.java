package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Repository.FoodRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static Foodfit.BackEnd.Exception.FoodException.*;

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
    public List<Food> searchFoods(String name) {
        List<Food> foods = foodRepository.findTop10ByNameContainingIgnoreCase(name);
        // 영어로 검색된 경우 처리
        if (containsEnglish(name)) {
            throw new EnglishSearchException("영어로 검색된 결과입니다.");
        }

        if (foods.isEmpty())  {
            throw new NoSearchResultException("검색 조건에 맞는 음식이 없습니다.");
        }

        return foods;
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
