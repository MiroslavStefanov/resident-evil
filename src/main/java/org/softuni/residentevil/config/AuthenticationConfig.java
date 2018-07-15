package org.softuni.residentevil.config;

import org.softuni.residentevil.core.authentication.Authentication;
import org.softuni.residentevil.services.UserService;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSession;

public class AuthenticationConfig {
    private final Authentication authentication;

    public AuthenticationConfig(UserService userService, HttpSession httpSession) {
        authentication = new Authentication(userService, httpSession);
    }

    @Bean
    public Authentication getAuthentication() {
        return this.authentication;
    }
}
