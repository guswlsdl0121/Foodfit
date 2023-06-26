package Foodfit.BackEnd.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2CreationWrapper implements OAuth2User, UserCreateInfo {

    private final OAuth2User oAuth2User;
    private final String uid;

    // 데이터 베이스 중복 저장 방지용 UID
    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    // 사용 x .. 권한 관련 구현하지 않음
    @Override
    @Deprecated(forRemoval = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }


}
