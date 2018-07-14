package org.softuni.residentevil.services;

import org.softuni.residentevil.models.entities.Capital;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CapitalServiceImpl implements CapitalService {
    private final CapitalRepository capitalRepository;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository) {
        this.capitalRepository = capitalRepository;
    }

    @Override
    public List<String> getAllCapitalsNames() {
        return this.capitalRepository
                .findAll()
                .stream()
                .map(Capital::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
