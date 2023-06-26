package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Auth.KakaoUserInfo;
import Foodfit.BackEnd.Auth.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;


    // 사용자 Oauth2 로그인시 호출되는 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2User oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 일단 카카오만 가능하도록..
        if(!provider.equals("kakao")){
            throw new OAuth2AuthenticationException(
                    String.format("invalid oauth2 registration ID : %s", provider));
        }
        oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        return oAuth2UserInfo;
    }
}
