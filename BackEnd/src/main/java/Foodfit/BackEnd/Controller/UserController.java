package Foodfit.BackEnd.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {
    @GetMapping("/login/kakao")
    public String getUserLoginForm() {
        return "redirect:/oauth2/authorization/kakao";
    }
}
