package org.softuni.residentevil.repositories;

import org.softuni.residentevil.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findFirstByUsername(String username);
}
