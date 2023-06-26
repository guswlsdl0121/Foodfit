package Foodfit.BackEnd.Auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class KakaoUser implements OAuth2User, UserCreateInfo {
    private Map<String, Object> attributes;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProfile;

    public KakaoUser(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
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


    @Override
    public String getUID() {
        return null;
    }
}
