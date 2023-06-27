package Foodfit.BackEnd.Auth;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserWrapper extends OAuth2User, ChangeableToUser {
}
