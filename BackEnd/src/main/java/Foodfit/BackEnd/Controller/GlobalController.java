package Foodfit.BackEnd.Controller;


import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.Aop.Annotations.LoginCheck;
import Foodfit.BackEnd.Domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@Slf4j
public class GlobalController {

    @GetMapping("/health-check")
    @AdditionalUserInfoCheck
    public String healthCheck(HttpServletRequest req){
        User user = (User)req.getAttribute("user");

        return "Server is Running!";
    }

}
