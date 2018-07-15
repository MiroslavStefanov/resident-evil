package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.UserLoginBindingModel;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.entities.User;
import org.softuni.residentevil.models.entities.UserRole;
import org.softuni.residentevil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String saveUser(UserRegisterBindingModel userRegisterBindingModel) {
        if(userRegisterBindingModel == null || !userRegisterBindingModel.doesMatch())
            return null;

        User user;

        try{
            user = this.modelMapper.map(userRegisterBindingModel, User.class);
            user = this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user != null ? user.getId() : null;
    }

    @Override
    public String logInUser(UserLoginBindingModel userLoginBindingModel) {
        if(userLoginBindingModel == null)
            return null;
        User user = this.userRepository.findFirstByUsername(userLoginBindingModel.getUsername());
        if(user == null || !user.getPassword().equals(userLoginBindingModel.getPassword()))
            return null;
        else
            return user.getId();
    }

    @Override
    public String getUserName(String userId) {
        if (userId == null)
            return  null;

        Optional<User> userCandidate = this.userRepository.findById(userId);
        return userCandidate.map(User::getUsername).orElse(null);
    }

    @Override
    public UserRole getUserRole(String userId) {
        Optional<User> userCandidate = this.userRepository.findById(userId);
        return userCandidate.map(User::getRole).orElse(null);
    }
}
