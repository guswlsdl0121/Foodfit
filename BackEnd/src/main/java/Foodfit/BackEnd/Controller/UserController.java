package Foodfit.BackEnd.Controller;

import org.springframework.stereotype.Controller;

@Controller
public class UserController {


    public String getUserLoginForm(){
        return "loginPage.html";
    }


}
