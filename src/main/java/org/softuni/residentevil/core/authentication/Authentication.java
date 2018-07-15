package org.softuni.residentevil.core.authentication;

import org.softuni.residentevil.models.entities.UserRole;
import org.softuni.residentevil.services.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class Authentication {
    private final UserService userService;
    private final HttpSession httpSession;

    public Authentication(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    public boolean isLoggedIn() {
        return this.httpSession.getAttribute("user-id") != null;
    }

    public boolean isAdmin() {
        return this.isLoggedIn() && this.userService.getUserRole(this.httpSession.getAttribute("user-id").toString()) == UserRole.ADMIN;
    }
}
