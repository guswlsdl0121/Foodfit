package Foodfit.BackEnd.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//수정 : 바로 redirect하도록 만듦
@Controller
public class UserController {
    @GetMapping("/kakao-login")
    public String getUserLoginForm() {
        return "redirect:/oauth2/authorization/kakao";
    }
}
