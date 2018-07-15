package org.softuni.residentevil.interceptors;

import org.softuni.residentevil.core.validation.annotations.PreAuthenticate;
import org.softuni.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Autowired
    public AuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(!handlerMethod.hasMethodAnnotation(PreAuthenticate.class))
            return true;

        boolean loggedIn = handlerMethod.getMethodAnnotation(PreAuthenticate.class).loggedIn();
        Object userIdCandidate = request.getSession().getAttribute("user-id");
        if(!loggedIn && userIdCandidate == null) {
            return true;
        }
        else if(loggedIn && userIdCandidate != null) {
            String role = handlerMethod.getMethodAnnotation(PreAuthenticate.class).inRole();
            if(role.equals(this.userService.getUserRole(userIdCandidate.toString()).toString())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
