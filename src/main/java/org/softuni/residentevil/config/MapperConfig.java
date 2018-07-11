package org.softuni.residentevil.config;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class MapperConfig {
    private final ModelMapper mapper;

    private final CapitalRepository capitalRepository;

    @Autowired
    public MapperConfig(CapitalRepository capitalRepository) {
        this.capitalRepository = capitalRepository;
        mapper = new ModelMapper();
        this.configure();
    }

    private void configure() {
        virusBindingMapping();
    }

    private void virusBindingMapping() {
        this.mapper.
                createTypeMap(VirusBindingModel.class, Virus.class).
                addMappings(m -> m.map(src->src
                        .getCapitals()
                        .stream()
                        .map(c->this.capitalRepository.findById(c.getId()).orElse(null))
                        .collect(Collectors.toSet()), Virus::setCapitals));
    }

    @Bean
    public ModelMapper getModelMapper() {
        return mapper;
    }
}
