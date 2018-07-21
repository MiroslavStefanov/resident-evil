package org.softuni.residentevil.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController extends BaseController {
    @GetMapping("/403")
    public ModelAndView forbidden() {
        return super.view("error/403");
    }

    @GetMapping("/unauthorized")
    public ModelAndView unauthorized() {
        return super.view("error/401");
    }
}
