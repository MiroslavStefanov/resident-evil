package org.softuni.residentevil.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.UserEditBindingModel;
import org.softuni.residentevil.models.view.UserViewModel;
import org.softuni.residentevil.services.RoleService;
import org.softuni.residentevil.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ModelAndView allUsers() {
        List<UserViewModel> allUsers = this.userService.getAll();
        return super.view("users/all", allUsers);
    }

    @GetMapping("/users/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id")String id) {
        UserViewModel viewModel = this.userService.getUser(id);
        UserEditBindingModel editBindingModel = this.modelMapper.map(viewModel, UserEditBindingModel.class);
        editBindingModel.setAllRoles(this.roleService.getAllRoleNames());
        return super.view("users/edit", editBindingModel);
    }

    @PostMapping("/users/edit/{id}")
    public ModelAndView editPost(
            @PathVariable(name = "id")String id,
            @Valid @ModelAttribute(name="viewModel")UserEditBindingModel userEditBindingModel,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return super.view("users/edit", userEditBindingModel);
        } else {
            this.userService.updateUser(id, userEditBindingModel);
            return super.redirect("/admin/users");
        }
    }
}
