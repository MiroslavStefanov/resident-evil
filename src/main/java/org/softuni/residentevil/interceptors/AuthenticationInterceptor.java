package org.softuni.residentevil.interceptors;

import org.softuni.residentevil.core.authentication.Authentication;
import org.softuni.residentevil.core.authentication.annotations.PreAuthenticate;
import org.softuni.residentevil.models.entities.UserRole;
import org.softuni.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final Authentication authentication;
    private boolean loggedIn;
    private String inRole;

    @Autowired
    public AuthenticationInterceptor(Authentication authentication) {
        this.authentication = authentication;
        loggedIn = false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method action = handlerMethod.getMethod();
        boolean ret = true;

        if(action.getDeclaringClass().isAnnotationPresent(PreAuthenticate.class)) {
            this.loggedIn = action.getDeclaringClass().getDeclaredAnnotation(PreAuthenticate.class).loggedIn();
            this.inRole = action.getDeclaringClass().getDeclaredAnnotation(PreAuthenticate.class).inRole();
            ret = false;
        }

        if(handlerMethod.hasMethodAnnotation(PreAuthenticate.class)) {
            this.loggedIn = handlerMethod.getMethodAnnotation(PreAuthenticate.class).loggedIn();
            this.inRole = handlerMethod.getMethodAnnotation(PreAuthenticate.class).inRole();
            ret = false;
        }

        if(!ret) {
            if(this.loggedIn) {
                if(this.authentication.isLoggedIn()) {
                    ret = (inRole.equals("ADMIN") && this.authentication.isAdmin()) || inRole.equals("USER");
                    if(!ret) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        try {
                            response.sendRedirect("/errors/401");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    try {
                        response.sendRedirect("/errors/403");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if(!this.authentication.isLoggedIn()) {
                    ret = true;
                } else {
                    response.setStatus(HttpStatus.SEE_OTHER.value());
                    try {
                        response.sendRedirect("/home");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return ret;
    }
}
