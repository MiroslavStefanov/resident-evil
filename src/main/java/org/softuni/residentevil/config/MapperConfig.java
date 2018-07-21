package org.softuni.residentevil.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.UserRegisterBindingModel;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.entities.Role;
import org.softuni.residentevil.models.entities.User;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.models.binding.UserEditBindingModel;
import org.softuni.residentevil.models.view.UserViewModel;
import org.softuni.residentevil.models.view.VirusShowViewModel;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.softuni.residentevil.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class MapperConfig {
    private final ModelMapper mapper;

    private final RoleService roleService;

    @Autowired
    public MapperConfig(CapitalRepository capitalRepository, RoleService roleService) {
        this.roleService = roleService;
        mapper = new ModelMapper();
        this.configure();
    }

    private void configure() {
        virusBindingMapping();
        virusViewMapping();
        virusReverseBindingMapping();
        userRegisterBindingMapping();
        userViewMapping();
        userViewEditBindingMapping();
    }

    private void virusBindingMapping() {
        this.mapper
                .createTypeMap(VirusBindingModel.class, Virus.class)
                .addMappings(m -> m.skip(Virus::setCapitals));
    }


    private void virusReverseBindingMapping() {
        this.mapper
                .createTypeMap(Virus.class, VirusBindingModel.class)
                .addMappings(m -> m.skip(VirusBindingModel::setCapitals));
    }

    private void virusViewMapping() {
        this.mapper
                .createTypeMap(Virus.class, VirusShowViewModel.class);
    }

    private void userRegisterBindingMapping() {
        this.mapper
                .createTypeMap(UserRegisterBindingModel.class, User.class);
    }

    private void userViewMapping() {
        Converter<Set<Role>, String> roleCon = ctx -> ctx
                .getSource()
                .stream()
                .findAny()
                .map(Role::getAuthority)
                .orElse(null);

        this.mapper
                .createTypeMap(User.class, UserViewModel.class)
                .addMappings(m -> m.using(roleCon).map(User::getAuthorities, UserViewModel::setRole));
    }

    private void userViewEditBindingMapping() {
        this.mapper
                .createTypeMap(UserViewModel.class, UserEditBindingModel.class)
                .addMappings(m -> m.skip(UserEditBindingModel::setAllRoles));
    }

    @Bean
    public ModelMapper getModelMapper() {
        return mapper;
    }
}
