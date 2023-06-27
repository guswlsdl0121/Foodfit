package Foodfit.BackEnd.Utils;

import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserProvider {

    private final UserRepository userRepository;


    /*
     * methodName : generateAccessToken
     * author : minturtle
     * description : SecurityContextHolder에 저장된 유저의 id를 기반으로 유저 객체를 조회한 후 리턴한다.
     * @param
     * @return 조회된 유저 객체 Optional
    */
    public Optional<User> getUserFromSecurityContext(){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findById(userId);
    }
}
