package org.softuni.residentevil.services;

import org.softuni.residentevil.models.binding.CapitalBindingViewModel;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
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
    public Set<CapitalBindingViewModel> getAll() {
        return this.capitalRepository
                .findAll()
                .stream()
                .map(cap -> new CapitalBindingViewModel(cap.getId(), cap.getName()))
                .collect(Collectors.toSet());
    }
}
