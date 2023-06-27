package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Auth.OAuth2UserWrapper;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.CookieAuthorizationRequestRepository;
import Foodfit.BackEnd.Repository.UserRepository;
import Foodfit.BackEnd.Utils.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


// 로그인 성공 후 부가 처리를 하는 클래스
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long uid = ((OAuth2UserWrapper) authentication.getPrincipal()).getUID();

        User findUser = userRepository.findUserByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("UID 값을 찾을 수 없습니다."));

        String accessToken = jwtTokenProvider.generateAccessToken(findUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(findUser);

        response.addHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", accessToken));
        response.addCookie(new Cookie("refresh-token", refreshToken));

        getRedirectStrategy().sendRedirect(request, response, "/");
    }
}
