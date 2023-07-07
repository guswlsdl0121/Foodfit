package Foodfit.BackEnd.Auth;

import Foodfit.BackEnd.Domain.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class KakaoUser implements OAuth2UserWrapper {
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


    @Override
    public <A> A getAttribute(String name) {
        return (A)attributes.get(name);
    }

    @Override
    public String getName() {
        return attributesProfile.get("nickname").toString();
    }

    @Override
    public Long getUID() {
        return Long.parseLong(attributes.get("id").toString());
    }

    //이미지 URL 관련 NULL처리
    @Override
    public User toUser() {
        //Object profileImageUrlObject = attributesProfile.get("profile_image_url");
        byte[] profileImageUrls = null;
        try{
            profileImageUrls = new ClassPathResource("defaultProfileImage.png").getContentAsByteArray();
        }catch (Exception e){
        }

        return User.builder()
                .name(getName())
                .uid(getUID())
                .profileImage(profileImageUrls)
                .build();
    }
}
