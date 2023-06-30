package Foodfit.BackEnd.Controller;


import Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck;
import Foodfit.BackEnd.DTO.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api")
@RestController
@Slf4j
public class GlobalController {
    @GetMapping("/health-check")
    @AdditionalUserInfoCheck
    public String healthCheck(HttpServletRequest req){
        UserDTO user = (UserDTO) req.getAttribute("user");
        log.info("userID = {}", user.getId());
        return "Server is Running!";
    }

    @GetMapping("/swagger")
    public  void swaggerDocument(HttpServletResponse res) throws IOException {
        res.sendRedirect("/swagger-ui/index.html");
    }

}
