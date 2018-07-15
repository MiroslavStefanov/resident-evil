package org.softuni.residentevil.services;

import org.softuni.residentevil.models.binding.UserLoginBindingModel;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.entities.UserRole;

public interface UserService {
    String saveUser(UserRegisterBindingModel userRegisterBindingModel);

    String logInUser(UserLoginBindingModel userLoginBindingModel);

    String getUserName(String userId);

    UserRole getUserRole(String userId);
}
