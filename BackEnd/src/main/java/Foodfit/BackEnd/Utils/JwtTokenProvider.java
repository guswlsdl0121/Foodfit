package Foodfit.BackEnd.Utils;

import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.Repository.UserRepository;
import io.jsonwebtoken.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final Long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
    private final Long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 14 * 1000L;

    /**
     * methodName : generateRefreshToken
     * author : Jaeyeop Jung
     * description : User를 통해서 RefreshToken을 생성하고 반환한다.
     *
     * @param user User Entity
     * @return RefreshToken 정보
     */
    public String generateRefreshToken(User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "REFRESH_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * methodName : generateAccessToken
     * author : Jaeyeop Jung
     * description : User를 통해서 AceesToken 생성하고 반환한다.
     *
     * @param user User Entity
     * @return AccessToken 정보
     */
    public String generateAccessToken(User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "ACCESS_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }

    /**
     * methodName : findUserIdByJwt
     * author : Jaeyeop Jung
     * description : Jwt 토큰에 담긴 Uesr.id를 찾아낸다.
     *
     * @param token Jwt Token
     * @return 토큰에 담긴 User.id
     */
    public Long findUserIdByJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Long userId = Long.valueOf(claims.getSubject());

        return userId;
    }

    /**
     * methodName : validateToken
     * author : Jaeyeop Jung
     * description : Token을 검증한다.
     *
     * @param token Jwt Token
     * @return 검증 결과
     */
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.error("JWT RefreshToken is empty");
        }
        return false;
    }



}