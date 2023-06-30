package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Domain.Food;
import Foodfit.BackEnd.Repository.FoodRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {
    private final FoodRepository foodRepository;

    /**
     * methodName : searchFoods
     * author : guswlsdl
     * description : 검색어에 대한 상위 10개 항목의 검색 결과를 가져온다.
     * @param  name
     * @return foodPage.getContent();
     */
    public List<Food> searchFoods(String name) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<Food> foodPage = foodRepository.findByNameContainingIgnoreCase(name, pageable);
        return foodPage.getContent();
    }
}
