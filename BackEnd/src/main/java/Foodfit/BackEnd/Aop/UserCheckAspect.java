package Foodfit.BackEnd.Aop;


import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Exception.NotFoundException;
import Foodfit.BackEnd.Exception.UnAuthorizedException;
import Foodfit.BackEnd.Utils.UserProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class UserCheckAspect {

    private final UserProvider userProvider;

    @Around("@annotation(Foodfit.BackEnd.Aop.Annotations.LoginCheck)")
    public Object LoginCheck(ProceedingJoinPoint pjp) throws Throwable {
        addUserInRequest(pjp);

        // 해당 클래스의 메소드 실행
        Object result = pjp.proceed();

        return result;
    }

    @Around("@annotation(Foodfit.BackEnd.Aop.Annotations.AdditionalUserInfoCheck)")
    public Object additionalUserInfoCheck(ProceedingJoinPoint pjp) throws Throwable {
        UserDTO userDTO = userProvider.getUser().orElseThrow(() -> new UnAuthorizedException("유저 정보를 찾을 수 없습니다."));

        userProvider.verifyIsFieldNotNull(userDTO, UserProvider.UserFields.age, UserProvider.UserFields.gender);

        addUserInRequest(pjp);
        // 해당 클래스의 메소드 실행
        Object result = pjp.proceed();

        return result;
    }

    private void addUserInRequest(ProceedingJoinPoint pjp) {
        for (Object obj : pjp.getArgs()) {
            if (!(obj instanceof HttpServletRequest)) {
                continue;
            }
            HttpServletRequest request = (HttpServletRequest) obj;
            UserDTO userDTO = userProvider.getUser().orElseThrow(() -> new UnAuthorizedException("유저 정보를 찾을 수 없습니다."));

            request.setAttribute("user", userDTO);

            break;

        }
    }
}
