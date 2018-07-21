package org.softuni.residentevil.services;

import org.softuni.residentevil.models.binding.UserEditBindingModel;
import org.softuni.residentevil.models.binding.UserLoginBindingModel;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    String saveUser(UserRegisterBindingModel userRegisterBindingModel);

    boolean updateUser(String id, UserEditBindingModel userEditBindingModel);

    String logInUser(UserLoginBindingModel userLoginBindingModel);

    String getUserName(String userId);

    List<UserViewModel> getAll();

    UserViewModel getUser(String userId);
}
