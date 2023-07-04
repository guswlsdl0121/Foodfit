package Foodfit.BackEnd.Utils;

import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Exception.NullFieldException;

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
    public Optional<UserDTO> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // 인증되지 않은 경우 예외 처리 또는 다른 처리를 수행할 수 있습니다.
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDTO) {
            return Optional.ofNullable((UserDTO)principal);
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
    public void verifyIsFieldNotNull(UserDTO user, UserFields ... fieldNames) throws NoSuchFieldException, IllegalAccessException {
        // Java Reflection을 사용해 테스트 진행
        Class<? extends UserDTO> userClass = user.getClass();

        for(UserFields fieldName : fieldNames){
            Field field = userClass.getDeclaredField(fieldName.name());

            boolean preAccess = field.canAccess(user); //접근 제한을 다시 원래대로 돌리기 위해 백업
            field.setAccessible(true);

            Object actual = field.get(user); // 해당 필드의 user의 값을 받아옴.
            field.setAccessible(preAccess);

            if(isZeroValue(actual)) throw new NullFieldException(String.format("%s 필드가 빈 값입니다.", fieldName.name()));
        }
    }

    private boolean isZeroValue(Object value){
        return (value == null) || (value instanceof Number && value.equals(0));
    }
    public  enum UserFields{
        id, name, age, gender, uid, profileImage;
    }
}
