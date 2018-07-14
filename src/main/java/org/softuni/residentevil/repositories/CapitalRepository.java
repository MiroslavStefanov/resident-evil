package org.softuni.residentevil.repositories;

import org.softuni.residentevil.models.entities.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {

    Capital findFirstByName(String name);
}
