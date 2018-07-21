package org.softuni.residentevil.controllers;

import org.softuni.residentevil.models.binding.UserLoginBindingModel;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return super.view("users/register", new UserRegisterBindingModel());
    }

    @PostMapping("/register")
    public ModelAndView registerPost(
            @Valid @ModelAttribute("viewModel") UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) {
        if(bindingResult.hasErrors()) {
            return super.view("users/register", userRegisterBindingModel);
        }
        else if(bindingResult.hasErrors() || !userRegisterBindingModel.doesMatch()) {
            bindingResult.rejectValue("confirmPassword", "Password doesn't match");
            return super.view("users/register", userRegisterBindingModel);
        } else {
            String loggedInUserId = this.userService.saveUser(userRegisterBindingModel);
            return super.redirect("/login");
        }
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error, @ModelAttribute("viewModel") UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult) {
        if(error != null){
            bindingResult.rejectValue("username", "");
            bindingResult.rejectValue("password", error);
        }
        return super.view("users/login", userLoginBindingModel);
    }


    @GetMapping("/logout")
    public ModelAndView logout() {
        return super.redirect("/");
    }
 }
