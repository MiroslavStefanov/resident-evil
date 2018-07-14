package org.softuni.residentevil.config;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.models.view.VirusShowViewModel;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    private final ModelMapper mapper;

    @Autowired
    public MapperConfig(CapitalRepository capitalRepository) {
        mapper = new ModelMapper();
        this.configure();
    }

    private void configure() {
        virusBindingMapping();
        virusViewMapping();
        virusReverseBindingMapping();
    }

    private void virusBindingMapping() {
        this.mapper
                .createTypeMap(VirusBindingModel.class, Virus.class)
                .addMappings(m -> m.skip(Virus::setCapitals));
    }


    private void virusReverseBindingMapping() {
        this.mapper
                .createTypeMap(Virus.class, VirusBindingModel.class)
                .addMappings(m -> m.skip(VirusBindingModel::setCapitals));
    }

    private void virusViewMapping() {
        this.mapper
                .createTypeMap(Virus.class, VirusShowViewModel.class);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return mapper;
    }
}
