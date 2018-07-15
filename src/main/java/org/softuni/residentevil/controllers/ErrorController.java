package org.softuni.residentevil.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/errors")
public class ErrorController extends BaseController {
    @GetMapping("/403")
    public ModelAndView forbidden() {
        return super.view("errors/403");
    }

    @GetMapping("/401")
    public ModelAndView unauthorized() {
        return super.view("errors/401");
    }
}
