package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.entities.Capital;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.models.view.VirusShowViewModel;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.softuni.residentevil.repositories.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveVirus(VirusBindingModel virusBindingModel) {
        try {
            Virus virus = this.modelMapper.map(virusBindingModel, Virus.class);
            Set<Capital> capitals = virusBindingModel.getCapitals()
                    .stream()
                    .map(this.capitalRepository::findFirstByName)
                    .collect(Collectors.toSet());

            virus.setCapitals(capitals);

            this.virusRepository.save(virus);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateVirus(String id, VirusBindingModel virusBindingModel) {
        if(id == null)
            return false;

        try{
            Virus virus = this.virusRepository.findById(id).orElse(null);

            if(virus != null)
                this.modelMapper.map(virusBindingModel, virus);
            else
                return false;

            Set<Capital> capitals = virusBindingModel.getCapitals()
                    .stream()
                    .map(this.capitalRepository::findFirstByName)
                    .collect(Collectors.toSet());
            virus.setCapitals(capitals);

            this.virusRepository.save(virus);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteVirus(String id) {
        if(id == null)
            return false;

        try{
            this.virusRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Set<VirusShowViewModel> getAllViruses() {
        return this.virusRepository
                .findAll()
                .stream()
                .map(v -> this.modelMapper.map(v, VirusShowViewModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public VirusBindingModel getVirus(String id) {
        if(id == null)
            return null;

        VirusBindingModel ret;
        Optional<Virus> virusCandidate = this.virusRepository.findById(id);
        ret = virusCandidate.map(virus -> this.modelMapper.map(virus, VirusBindingModel.class)).orElse(null);

        if(ret != null)
            ret.setCapitals(
                    virusCandidate
                            .get()
                            .getCapitals()
                            .stream()
                            .map(Capital::getName)
                            .collect(Collectors.toList())
            );

        return ret;
    }
}
