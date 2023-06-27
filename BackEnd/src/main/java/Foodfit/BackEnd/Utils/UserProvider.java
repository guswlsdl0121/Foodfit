package Foodfit.BackEnd.Utils;

import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
     * update : guswls (예외처리를 위한 세부 동작 정의)
     * description : SecurityContextHolder에 저장된 유저의 id를 기반으로 유저 객체를 조회한 후 리턴한다.
     * @param
     * @return 조회된 유저 객체 Optional
    */
    public Optional<User> getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // 인증되지 않은 경우 예외 처리 또는 다른 처리를 수행할 수 있습니다.
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Long) {
            Long userId = (Long) principal;
            return userRepository.findById(userId);
        } else {
            // 사용자 ID를 가져올 수 없는 경우 예외 처리 또는 다른 처리를 수행할 수 있습니다.
            return Optional.empty();
        }
    }
}
