package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.UserEditBindingModel;
import org.softuni.residentevil.models.binding.UserLoginBindingModel;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.entities.User;
import org.softuni.residentevil.models.view.UserViewModel;
import org.softuni.residentevil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String saveUser(UserRegisterBindingModel userRegisterBindingModel) {
        if(userRegisterBindingModel == null || !userRegisterBindingModel.doesMatch())
            return null;

        User user;

        try{
            user = this.modelMapper.map(userRegisterBindingModel, User.class);
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            user.getAuthorities().add(this.roleService.getRole("USER"));
            user = this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user.getId();
    }

    @Override
    public boolean updateUser(String id, UserEditBindingModel userEditBindingModel) {
        if(id == null)
            return false;

        try{
            User user = this.userRepository.findById(id).orElse(null);

            if(user != null)
                user.setAuthorities(new HashSet<>(){{
                    add(roleService.getRole(userEditBindingModel.getRole()));
                }});
            else
                return false;

            this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
    public List<UserViewModel> getAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserViewModel getUser(String userId) {
        if (userId == null)
            return  null;

        Optional<User> userCandidate = this.userRepository.findById(userId);
        return userCandidate.map(user->this.modelMapper.map(user, UserViewModel.class)).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findFirstByUsername(s);
    }
}
