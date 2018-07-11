package org.softuni.residentevil.controllers;

import org.softuni.residentevil.models.binding.CapitalBindingViewModel;
import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.services.CapitalService;
import org.softuni.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;
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
        modelAndView.addObject("title", "Spread a new Virus!");
        modelAndView.addObject("allCapitals", this.capitalService.getAll());
        modelAndView.addObject("action", "Create Virus!");
        modelAndView.addObject("virus", new VirusBindingModel());

        return this.view("viruses/add", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView createPost(@Valid @ModelAttribute("virus") VirusBindingModel virusBindingModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if(bindingResult.hasErrors()) {
            Set<CapitalBindingViewModel> allCapitals = this.capitalService.getAll();
            virusBindingModel.fillCapitalsName(allCapitals);
            modelAndView.addObject("virus", virusBindingModel);
            modelAndView.addObject("title", "Spread a new Virus!");
            modelAndView.addObject("allCapitals", allCapitals);
            modelAndView.addObject("action", "Create Virus!");
            return this.view("viruses/add", modelAndView);
        } else{
            this.virusService.saveVirus(virusBindingModel);
            return this.redirect("/");
        }
    }
}
