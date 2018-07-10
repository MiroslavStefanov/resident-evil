package org.softuni.residentevil.repositories;

import org.softuni.residentevil.models.entities.Virus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirusRepository extends JpaRepository<Virus, Long> {
}
