package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Auth.OAuth2UserInfo;
import Foodfit.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void addOAuth2User(OAuth2UserInfo oAuth2UserInfo){


    }
}
