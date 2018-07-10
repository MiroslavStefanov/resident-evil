package org.softuni.residentevil.controllers;

import org.softuni.residentevil.models.entities.Capital;
import org.softuni.residentevil.models.entities.Magnitude;
import org.softuni.residentevil.models.entities.Mutation;
import org.softuni.residentevil.models.entities.Virus;
import org.softuni.residentevil.services.CapitalService;
import org.softuni.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {
    private final VirusService virusService;
    private final CapitalService capitalService;

    @Autowired
    public VirusController(VirusService virusService, CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping("/add")
    public ModelAndView create(ModelAndView modelAndView) {
        Virus virus = new Virus();
        List<Capital> capitals = this.capitalService.getAll();
        virus.setCapitals(new HashSet<>(capitals));
        virus.setDeadly(true);
        virus.setMutation(Mutation.ZOMBIE);
        virus.setMagnitude(Magnitude.Low);
        virus.setReleasedOn(LocalDate.now().minusDays(1));
        modelAndView.addObject("title", "Spread a new Virus!");
        modelAndView.addObject("virus", virus);
        modelAndView.addObject("allCapitals", capitals);
        modelAndView.addObject("action", "Create Virus!");
        return this.view("viruses/add", modelAndView);
    }
}
