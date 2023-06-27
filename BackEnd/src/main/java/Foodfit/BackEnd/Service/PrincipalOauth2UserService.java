package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Auth.KakaoUser;
import Foodfit.BackEnd.Auth.OAuth2UserWrapper;
import Foodfit.BackEnd.Domain.User;
import Foodfit.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // 사용자 Oauth2 로그인시 호출되는 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserWrapper oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 일단 카카오만 가능하도록..
        if(!provider.equals("kakao")){
            throw new OAuth2AuthenticationException(
                    String.format("invalid oauth2 registration ID : %s", provider));
        }
        oAuth2UserInfo = new KakaoUser(oAuth2User.getAttributes());


        // 만약 저장된 유저가 없다면 유저를 저장
        if(isNewUser(oAuth2UserInfo)){
            User newUser = oAuth2UserInfo.toUser();
            userRepository.save(newUser);
        }


        return oAuth2UserInfo;
    }

    private boolean isNewUser(OAuth2UserWrapper oAuth2UserInfo) {
        return userRepository.findUserByUid(oAuth2UserInfo.getUID()).isEmpty();
    }
}
