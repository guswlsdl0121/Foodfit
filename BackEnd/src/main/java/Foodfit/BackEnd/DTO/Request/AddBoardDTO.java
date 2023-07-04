package Foodfit.BackEnd.DTO.Request;

import Foodfit.BackEnd.Domain.BoardImage;
import Foodfit.BackEnd.Domain.Food;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public record AddBoardDTO (String title, String description, List<byte []> images, List<Long> tags){
    public List<Long> toFoodIdList() {
        return new ArrayList<>(tags);
    }

    public List<byte []> toImageList(){
        return new ArrayList<>(new ArrayList<byte[]>(images));
    }
}
