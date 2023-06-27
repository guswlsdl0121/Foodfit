package Foodfit.BackEnd.Config;

import Foodfit.BackEnd.Filter.JwtAuthenticationFilter;
import Foodfit.BackEnd.Service.PrincipalOauth2UserService;
import Foodfit.BackEnd.Utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PrincipalOauth2UserService oauth2UserService;
    private final AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

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
                    oauth2Login
                            .authorizationEndpoint(
                                    config->config.baseUri("/oauth2/authorization") // 소셜 로그인 url

                            )
                            .userInfoEndpoint(config->config.userService(oauth2UserService))
                            .successHandler(oAuth2AuthenticationSuccessHandler);
                })
                .rememberMe(AbstractHttpConfigurer::disable)  //remember me disable
                .sessionManagement(sessionManagement->{  //session disable
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .logout(config->config.clearAuthentication(true).deleteCookies("JSESSIONID")) //logout 설정
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
