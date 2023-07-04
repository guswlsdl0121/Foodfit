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
     * @return foods;
     */
    @TimeLog
    public List<Food> searchFoods(String name) {
        List<Food> foods = foodRepository.findTop10ByNameContainingIgnoreCase(name);

        //예외 처리
        if (foods.isEmpty()) throw new NoFoodException();

        return foods;
    }

}
