package Foodfit.BackEnd.Filter;

import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import Foodfit.BackEnd.Utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// JWT 토큰으로 인증하고 SecurityContextHolder에 추가하는 필터를 설정하는 클래스
//TODO [HJ] 디버깅을 위한 오류 내역 상세 출력, 추후 수정
@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){      //header에 AUTHORIZATION이 없거나, Bearer로 시작하지 않으면 filter
            log.error("header가 없거나, 형식이 틀립니다. - {}", authorizationHeader);
            filterChain.doFilter(request, response);
            return;
        }
        String token;
        try {
            token = authorizationHeader.split(" ")[1].trim();
        } catch (Exception e) {
            log.error("토큰을 분리하는데 실패했습니다. - {}", authorizationHeader);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 분리를 실패하였습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰이 Valid한지 확인하기
        if(!tokenProvider.validateToken(token)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유요하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = tokenProvider.findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("잘못된 token입니다."));

        //AuthenticationToken 만들기
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(user.getId(), null);

        //디테일 설정하기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
