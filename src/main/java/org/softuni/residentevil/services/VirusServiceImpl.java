package org.softuni.residentevil.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.entities.Capital;
import org.softuni.residentevil.models.entities.Magnitude;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.models.view.VirusShowViewModel;
import org.softuni.residentevil.models.view.json.Feature;
import org.softuni.residentevil.models.view.json.FeatureCollection;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.softuni.residentevil.repositories.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper, Gson gson) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
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

    private double getMagnitudeRadius(Magnitude magnitude) {
        if(magnitude == Magnitude.Low)
            return 5.6;
        if(magnitude == Magnitude.Medium)
            return 6.4;
        return 7;
    }

    @Override
    public String getAllMapViruses() {
        FeatureCollection featureCollection = new FeatureCollection();
        List<Virus> allViruses = this.virusRepository.findAll();
        List<Feature> features = new ArrayList<>();
        for (Virus virus : allViruses) {
            for (Capital capital : virus.getCapitals()) {
                features.add(new Feature(
                        capital.getLatitude(),
                        capital.getLongitude(),
                        this.getMagnitudeRadius(virus.getMagnitude()))
                );
            }
        }
        featureCollection.setFeatures(features);
        return this.gson.toJson(featureCollection);
    }
}
