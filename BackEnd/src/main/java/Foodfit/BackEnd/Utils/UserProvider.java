package Foodfit.BackEnd.Utils;

import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.Reflection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Optional;

@Component

public class UserProvider {


    /*
     * author : minturtle
     * update : guswls (예외처리를 위한 세부 동작 정의)
     * description : SecurityContextHolder에 저장된 유저 객체를 조회한 후 리턴한다.
     * @param
     * @return 조회된 유저 객체 Optional
    */
    public Optional<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // 인증되지 않은 경우 예외 처리 또는 다른 처리를 수행할 수 있습니다.
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return Optional.ofNullable((User)principal);
        }


        return Optional.empty();

    }


    /*
     * methodName : verifyIsFieldNotNull
     * author : minturtle
     * description : 필드 이름을 받아, 전달 받은 모든 필드가 Not NULL인지 확인하는 메서드
     * @param userEntity(User), fieldNames(String ...)
     * @return 모두 not null이라면 true, 하나라도 null인 필드가 있다면 false
     */
    public boolean verifyIsFieldNotNull(User user, UserFields ... fieldNames) throws NoSuchFieldException, IllegalAccessException {
        // Java Reflection을 사용해 테스트 진행
        Class<? extends User> userClass = user.getClass();

        for(UserFields fieldName : fieldNames){
            Field field = userClass.getField(fieldName.name());

            boolean preAccess = field.canAccess(user); //접근 제한을 다시 원래대로 돌리기 위해 백업
            field.setAccessible(true);

            Object actual = field.get(user); // 해당 필드의 user의 값을 받아옴.
            field.setAccessible(preAccess);

            if(actual == null) return false;
        }
        return true;
    }


    public  enum UserFields{
        id, name, age, gender, uid;
    }
}
