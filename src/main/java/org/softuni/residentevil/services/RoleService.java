package org.softuni.residentevil.services;

import org.softuni.residentevil.models.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String name);

    List<String> getAllRoleNames();
}
