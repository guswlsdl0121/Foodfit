package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Auth.UserCreateInfo;
import Foodfit.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public void addUser(UserCreateInfo userInfo){


    }
}