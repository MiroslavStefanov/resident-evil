package org.softuni.residentevil.services;

import org.softuni.residentevil.models.entities.Capital;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CapitalServiceImpl implements CapitalService {
    private final CapitalRepository capitalRepository;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository) {
        this.capitalRepository = capitalRepository;
    }

    @Override
    public List<Capital> getAll() {
        return this.capitalRepository.findAll();
    }
}
