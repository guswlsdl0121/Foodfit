package Foodfit.BackEnd.Controller;

import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.DTO.Request.UpdateUserRequest;
import Foodfit.BackEnd.DTO.Response.UserResponse;
import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.DTO.UserUpdateDTO;
import Foodfit.BackEnd.Domain.Gender;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "사용자 관련 API", description = "사용자 정보 조회, 정보 변경, 카카오 로그인용 하이퍼링크입니다.")
public class UserController {

    private final UserService userService;


    @GetMapping("/user")
    @Operation(description = "사용자 정보 조회입니다.")
    @LoginCheck
    public UserResponse getUser(HttpServletRequest req){
        UserDTO userDTO = (UserDTO) req.getAttribute("user");

        return UserResponse.builder()
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .gender(userDTO.getGender())
                .profileImage(userDTO.getProfileImage())
                .build();

    }

    @GetMapping("/login/kakao")
    @Operation(description = "카카오로 로그인하기 버튼 하이퍼링크용 URL입니다.")
    public void kakaoLogin(HttpServletResponse res) throws IOException {

        res.sendRedirect("/oauth2/authorization/kakao");
    }

    @PutMapping("/user")
    @LoginCheck
    @Operation(description = "유저 정보 업데이트 URL입니다. 바뀌지 않은 값이여도 모두 넘겨주어야합니다.\n" +
            "gender의 값으로 \"남\"이 들어가는 단어는 남성, \"여\"가 들어가는 단어는 여자로 인식해 저장합니다. 또는 MALE, FEMALE도 가능합니다.")
    public ResponseEntity updateUser(HttpServletRequest req, @RequestBody UpdateUserRequest reqBody){
        final UserDTO userDTO = (UserDTO) req.getAttribute("user");

        Gender gender = getGenderFromRequest(reqBody);

        UserUpdateDTO dto = UserUpdateDTO.builder()
                .name(reqBody.getName())
                .gender(gender)
                .age(reqBody.getAge())
                .build();

        userService.updateUser(userDTO.getId(), dto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(description = "프로필 이미지 변경 URL입니다. \n")
    @PutMapping("/user/image")
    @LoginCheck
    public ResponseEntity updateUser( @RequestParam(required = false) MultipartFile image,
                                      HttpServletRequest req) throws Exception {
        final UserDTO userDTO = (UserDTO) req.getAttribute("user");
        userService.updateProfileImage(userDTO.getId(), image);
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
