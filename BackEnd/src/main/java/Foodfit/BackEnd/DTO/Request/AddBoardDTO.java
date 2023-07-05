package Foodfit.BackEnd.DTO.Request;

import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record AddBoardDTO (String content, List<MultipartFile> images, List<Long> tags){
    public List<Long> toFoodIdList() {return new ArrayList<>(tags);}
    public List<MultipartFile> toImageList(){return new ArrayList<>(new ArrayList<>(images));
    }
    public String getContent() {return content; }
}
