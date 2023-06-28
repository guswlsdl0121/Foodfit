package Foodfit.BackEnd.Aop;


import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Utils.UserProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

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
        User user = userProvider.getUser().orElseThrow(()->new NoSuchElementException("유저 정보를 찾을 수 없습니다."));

        userProvider.verifyIsFieldNotNull(user,  UserProvider.UserFields.age, UserProvider.UserFields.gender);

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
            User user = userProvider.getUser().orElseThrow(()->new NoSuchElementException("유저 정보를 찾을 수 없습니다."));
            request.setAttribute("user", user);

            break;

        }
    }
}
