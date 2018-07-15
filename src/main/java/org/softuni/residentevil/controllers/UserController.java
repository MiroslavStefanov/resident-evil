package org.softuni.residentevil.controllers;

import org.softuni.residentevil.core.validation.annotations.PreAuthenticate;
import org.softuni.residentevil.models.binding.UserLoginBindingModel;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthenticate(loggedIn = false)
    @GetMapping("/register")
    public ModelAndView register() {
        return super.view("users/register", new UserRegisterBindingModel());
    }

    @PreAuthenticate(loggedIn = false)
    @PostMapping("/register")
    public ModelAndView registerPost(
            @Valid @ModelAttribute("viewModel") UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        if(bindingResult.hasErrors()) {
            return super.view("users/register", userRegisterBindingModel);
        }
        else if(bindingResult.hasErrors() || !userRegisterBindingModel.doesMatch()) {
            bindingResult.rejectValue("confirmPassword", "Password doesn't match");
            return super.view("users/register", userRegisterBindingModel);
        } else {
            String loggedInUserId = this.userService.saveUser(userRegisterBindingModel);
            if(loggedInUserId != null) {
                httpSession.setAttribute("user-id", loggedInUserId);
            }
            return super.redirect("home/home");
        }
    }

    @PreAuthenticate(loggedIn = false)
    @GetMapping("/login")
    public ModelAndView login() {
        return super.view("users/login", new UserLoginBindingModel());
    }

    @PreAuthenticate(loggedIn = false)
    @PostMapping("/login")
    public ModelAndView loginPost(
            @ModelAttribute("viewModel") UserLoginBindingModel userLoginBindingModel,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        String loggedInUserId = this.userService.logInUser(userLoginBindingModel);
        if(loggedInUserId == null) {
            bindingResult.rejectValue("username", "Invalid username or password");
            bindingResult.rejectValue("password", "Invalid username or password");
            return super.view("users/login", userLoginBindingModel);
        } else {
            httpSession.setAttribute("user-id", loggedInUserId);
            return super.redirect("/viruses/add");
        }
    }

    @PreAuthenticate(loggedIn = true)
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession httpSession) {
        httpSession.removeAttribute("user-id");
        return super.redirect("/");
    }
 }
