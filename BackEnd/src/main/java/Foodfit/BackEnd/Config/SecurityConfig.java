package Foodfit.BackEnd.Config;

import Foodfit.BackEnd.Filter.JwtAuthenticationFilter;
import Foodfit.BackEnd.Service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((authorizeHttpRequest)->{
                    authorizeHttpRequest
                            .requestMatchers("/api/health-check").authenticated()
                            .requestMatchers("/api/login/**", "/api/swagger", "/swagger-ui/**", "/v3/**").permitAll()
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
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
