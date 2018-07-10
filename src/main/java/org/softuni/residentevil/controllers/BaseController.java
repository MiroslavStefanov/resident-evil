package org.softuni.residentevil.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
    private static final String BASE_PAGE_LAYOUT_VIEW_NAME = "base-layout";

    public ModelAndView view(String viewName, ModelAndView modelAndView) {
        modelAndView.addObject("view", viewName);
        modelAndView.setViewName(BASE_PAGE_LAYOUT_VIEW_NAME);

        return modelAndView;
    }

    public ModelAndView view(String viewName) {
        return this.view(viewName, new ModelAndView());
    }

    public ModelAndView redirect(String location) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:" + location);

        return modelAndView;
    }
}
