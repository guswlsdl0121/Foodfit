package Foodfit.BackEnd.Aop.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
 * author : minturtle
 * description : User가 잘 로그인되었는지 확인하고, HttpServletRequest 객체에 User 객체를 저장한다.
 *               req.getAttribute("user"); 를 통해 유저 객체를 얻을 수 있다.
* */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LoginCheck {
}
