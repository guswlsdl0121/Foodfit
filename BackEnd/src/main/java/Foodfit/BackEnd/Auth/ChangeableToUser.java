package Foodfit.BackEnd.Auth;

import Foodfit.BackEnd.Domain.User;

public interface ChangeableToUser {
    User toUser();
    Long getUID();
    String getName();
}
