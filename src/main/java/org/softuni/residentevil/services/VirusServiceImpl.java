package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.repositories.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveVirus(VirusBindingModel virusBindingModel) {
        Virus virus = this.modelMapper.map(virusBindingModel, Virus.class);
        this.virusRepository.save(virus);
    }
}
