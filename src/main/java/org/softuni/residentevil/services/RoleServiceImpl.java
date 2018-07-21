package org.softuni.residentevil.services;

import org.softuni.residentevil.models.entities.Role;
import org.softuni.residentevil.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role getRole(String name) {
        Role role = this.roleRepository.findFirstByAuthority(name);
        if(role == null) {
            role = new Role();
            role.setAuthority(name);
            role = this.roleRepository.save(role);
        }
        return role;
    }

    @Override
    public List<String> getAllRoleNames() {
        return this.roleRepository
                .findAll()
                .stream()
                .filter(role -> !role.getAuthority().equals("ROOT"))
                .map(Role::getAuthority)
                .collect(Collectors.toList());
    }
}
