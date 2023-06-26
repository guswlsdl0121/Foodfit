package Foodfit.BackEnd.Auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class KakaoUserInfo implements OAuth2User {
    private Map<String, Object> attributes;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProfile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 사용 x .. 권한 관련 구현하지 않음
    @Override
    @Deprecated(forRemoval = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    // 사용 x
    @Override
    @Deprecated(forRemoval = true)
    public <A> A getAttribute(String name) {
        return null;
    }

    @Override
    public String getName() {
        return attributesProfile.get("nickname").toString();
    }

}
