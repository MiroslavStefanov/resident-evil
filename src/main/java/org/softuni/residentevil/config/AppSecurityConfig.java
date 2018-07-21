package org.softuni.residentevil.config;

import org.softuni.residentevil.models.entities.User;
import org.softuni.residentevil.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final String rootUserId;

    public AppSecurityConfig(UserService userService, String rootUserId) {
        this.userService = userService;
        this.rootUserId = rootUserId;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/login", "/register").anonymous()
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .antMatchers("/viruses/add, /viruses/edit, viruses/delete")
                        .hasAnyAuthority("MODERATOR", "ADMIN")
                    .antMatchers("/admin/users/edit/{userId}")
                        .access("hasAuthority('ADMIN') and @appSecurityConfig.checkIfAuthorizedToEdit(principal, #userId)")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home", true)
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/unauthorized")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
        ;
    }

    public boolean checkIfAuthorizedToEdit(User principal, String userId) {
        if(userId == null)
            return false;

        if(userId.equals(this.rootUserId))
            return false;

        return !principal.getId().equals(userId);
    }
}
