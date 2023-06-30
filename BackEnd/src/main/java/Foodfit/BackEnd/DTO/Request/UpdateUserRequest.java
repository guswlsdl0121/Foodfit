package Foodfit.BackEnd.DTO.Request;

import lombok.Data;

@Data
public class UpdateUserRequest{
    private String name;
    private int age;
    private String gender;

}
