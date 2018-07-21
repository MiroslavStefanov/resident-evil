package org.softuni.residentevil.core.seeders;

import org.softuni.residentevil.models.entities.User;
import org.softuni.residentevil.repositories.UserRepository;
import org.softuni.residentevil.services.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;

@Component
@Transactional
public class DatabaseSeeder {
    private static final String ROOT_USER_NAME = "root";
    private static final String ROOT_USER_PASSWORD = "rootPsw1234";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DatabaseSeeder(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener
    public void onContextRefresh(ContextRefreshedEvent event){
        seedRootUser();
    }

    private void seedRootUser(){
        if(this.userRepository.count() == 0) {
            User root = new User();
            root.setAuthorities(new LinkedHashSet<>(){{
                add(roleService.getRole("ROOT"));
                add(roleService.getRole("ADMIN"));
            }});
            root.setUsername(ROOT_USER_NAME);
            root.setPassword(bCryptPasswordEncoder.encode(ROOT_USER_PASSWORD));
            root.setEmail("root@root.root");
            root = this.userRepository.save(root);
        }
    }

    @Bean
    public String getRootUserId() {
        return this.userRepository.findFirstByUsername(ROOT_USER_NAME).getId();
    }
}
