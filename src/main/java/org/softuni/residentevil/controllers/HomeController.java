package org.softuni.residentevil.controllers;

import org.softuni.residentevil.core.authentication.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
    private final Authentication authentication;

    public HomeController(Authentication authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/")
    public ModelAndView index() {
        if(this.authentication.isLoggedIn())
            return super.redirect("/home");
        return super.view("home/index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        if(!this.authentication.isLoggedIn())
            return super.redirect("/");
        return super.view("home/home");
    }
}
