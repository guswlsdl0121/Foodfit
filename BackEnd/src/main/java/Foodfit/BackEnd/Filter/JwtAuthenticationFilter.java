package Foodfit.BackEnd.Filter;

import Foodfit.BackEnd.DTO.UserDTO;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Exception.AuthorizeExceptionMessages;
import Foodfit.BackEnd.Exception.UnAuthorizedException;
import Foodfit.BackEnd.Repository.UserRepository;
import Foodfit.BackEnd.Utils.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


// JWT 토큰으로 인증하고 SecurityContextHolder에 추가하는 필터를 설정하는 클래스
//TODO [HJ] 디버깅을 위한 오류 내역 상세 출력, 추후 수정
@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    // try catch문을 공통 처리 하도록 변경
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            process(request, response, filterChain);
        }catch (Exception e){ response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()); }
    }

    private void process(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token;

        //header에 AUTHORIZATION이 없거나, Bearer로 시작하지 않는지 체크
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 분리
        try {
            token = authorizationHeader.split(" ")[1].trim();
        } catch (Exception e) {
            throw new UnAuthorizedException(AuthorizeExceptionMessages.UNAVAILABLE_TOKEN.MESSAGE);
        }

        //토큰이 Valid한지 확인하기
        tokenProvider.validateToken(token);

        Long userId = tokenProvider.findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(AuthorizeExceptionMessages.CANNOT_FIND_USER_FROM_TOKEN.MESSAGE));

        // DTO로 데이터를 전달
        UserDTO userDto = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .profileImage(user.getProfileImage())
                .gender(user.getGender())
                .build();

        Collection<SimpleGrantedAuthority> authorities = extractAuthorities(tokenProvider.getClaim(token));

        //AuthenticationToken 만들기
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userDto, "", authorities);

        //디테일 설정하기. SecurityContextHolder에 유저 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }


    private Collection<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
