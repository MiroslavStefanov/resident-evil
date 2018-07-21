package org.softuni.residentevil.repositories;

import org.softuni.residentevil.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findFirstByAuthority(String authority);
}
