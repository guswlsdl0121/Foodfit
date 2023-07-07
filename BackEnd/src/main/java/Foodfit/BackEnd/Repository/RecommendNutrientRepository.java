package Foodfit.BackEnd.Repository;


import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.RecommendNutrient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendNutrientRepository extends CrudRepository<RecommendNutrient, Long> {

    @Query("select n from RecommendNutrient n where n.startAge <= :age and n.endAge >= :age and n.gender = :gender")
    List<RecommendNutrient> findRecommendNutrient(@Param("age") int age, @Param("gender") Gender gender);

}
