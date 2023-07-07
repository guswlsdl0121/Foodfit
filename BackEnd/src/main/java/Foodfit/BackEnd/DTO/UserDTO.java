package Foodfit.BackEnd.DTO;

import Foodfit.BackEnd.Domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Base64;


@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private int age;
    private Gender gender;
    private byte[] profileImage; // byte 배열로 변경

    public static String byteToUrl(byte[] image){ // byte 배열로 변경

        String base64Encode = Base64.getEncoder().encodeToString(image);
        base64Encode = "data:image/png;base64," + base64Encode;

        return base64Encode;
    }
}
