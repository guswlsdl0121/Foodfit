package Foodfit.BackEnd.DTO.Response;

import Foodfit.BackEnd.Domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserResponse {
    private String name;
    private int age;
    private Gender gender;
    private String profileImage;
}
