package Foodfit.BackEnd.Aop.Annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/*
 * author : minturtle
 * description : User가 잘 로그인되었는지 확인하고, User의 추가 입력해야 하는 정보가 Zero Value(null, 0..)인지 확인한다.
 *               빈 값이라면 NullFieldException을 throw해 컨트롤러까지 요청이 전달되지 않는다.
 *               req.getAttribute("user"); 를 통해 유저 객체를 얻을 수 있다.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AdditionalUserInfoCheck {
}
