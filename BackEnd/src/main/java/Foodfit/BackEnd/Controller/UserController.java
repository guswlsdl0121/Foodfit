package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.DTO.Request.UpdateUserRequest;
import Foodfit.BackEnd.DTO.Response.UserResponse;
import Foodfit.BackEnd.DTO.UserUpdateDTO;
import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.InputMismatchException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user")
    @LoginCheck
    public UserResponse getUser(HttpServletRequest req){
        User user = (User) req.getAttribute("user");

        return UserResponse.builder()
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .profileImage(user.getProfileImage())
                .build();

    }

    @GetMapping("/login/kakao")
    @Operation(description = "카카오로 로그인하기 버튼 하이퍼링크용 URL입니다.")
    public void kakaoLogin(HttpServletResponse res) throws IOException {

        res.sendRedirect("/oauth2/authorization/kakao");
    }

    @PutMapping("/user/")
    @LoginCheck
    @Operation(description = "유저 정보 업데이트 URL입니다. 바뀌지 않은 값이여도 모두 넘겨주어야합니다.\n" +
            "gender의 값으로 \"남\"이 들어가는 단어는 남성, \"여\"가 들어가는 단어는 여자로 인식해 저장합니다. 또는 MALE, FEMALE도 가능합니다.")
    public ResponseEntity updateUser(HttpServletRequest req, @RequestBody UpdateUserRequest reqBody){
        final User user = (User) req.getAttribute("user");

        Gender gender = getGenderFromRequest(reqBody);

        UserUpdateDTO dto = UserUpdateDTO.builder()
                .name(reqBody.getName())
                .gender(gender)
                .age(reqBody.getAge())
                .build();

        userService.updateUser(user.getId(), dto);

        return new ResponseEntity(HttpStatus.OK);
    }

    private Gender getGenderFromRequest(UpdateUserRequest reqBody) {
        Gender gender = null;
        if(reqBody.getGender().contains("남") || reqBody.getGender().toUpperCase().equals("MALE")){
            gender = Gender.MALE;
        }
        else if(reqBody.getGender().contains("여") || reqBody.getGender().toUpperCase().equals("FEMALE")){
            gender = Gender.FEMALE;
        }else{
            throw new IllegalArgumentException("잘못된 성별 값입니다.");
        }

        return gender;
    }


}
