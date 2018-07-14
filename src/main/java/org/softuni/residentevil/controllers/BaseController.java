package org.softuni.residentevil.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
    private static final String BASE_PAGE_LAYOUT_VIEW_NAME = "base-layout";

    protected ModelAndView view(String viewName) {
        return this.view(viewName, null);
    }

    protected ModelAndView view(String viewName, Object viewModel) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("viewModel", viewModel);
        modelAndView.addObject("view", viewName);
        modelAndView.setViewName(BASE_PAGE_LAYOUT_VIEW_NAME);

        return modelAndView;
    }

    protected ModelAndView redirect(String location) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:" + location);

        return modelAndView;
    }
}
