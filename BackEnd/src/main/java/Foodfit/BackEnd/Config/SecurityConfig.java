package Foodfit.BackEnd.Config;

import Foodfit.BackEnd.Service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{


    private final PrincipalOauth2UserService oauth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((authorizeHttpRequest)->{
                    authorizeHttpRequest
                            //.requestMatchers("/api/**").authenticated()
                            .anyRequest().permitAll(); // 실사용시에 위 코드로 변경 필요

                })
                .oauth2Login(oauth2Login->{
                    oauth2Login.loginPage("/loginForm")
                            .defaultSuccessUrl("/")
                            .failureUrl("/loginForm")
                            .userInfoEndpoint(oAuth2LoginConfigurer->{
                                oAuth2LoginConfigurer.userService(oauth2UserService);
                            });

                })
                .build();
    }


}
