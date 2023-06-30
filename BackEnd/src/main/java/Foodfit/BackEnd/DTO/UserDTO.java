package Foodfit.BackEnd.DTO;

import Foodfit.BackEnd.Domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private int age;
    private Gender gender;
    private String profileImage;
}
