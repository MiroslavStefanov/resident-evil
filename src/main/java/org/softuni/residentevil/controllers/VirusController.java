package org.softuni.residentevil.controllers;

import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.view.VirusCreateViewModel;
import org.softuni.residentevil.services.CapitalService;
import org.softuni.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    public ModelAndView create() {
        VirusCreateViewModel viewModel = new VirusCreateViewModel(
                "Spread a new Virus",
                "create",
                this.capitalService.getAllCapitalsNames()
        );
        return this.view("viruses/add", viewModel);
    }

    @PostMapping("/add")
    public ModelAndView createPost(
            @Valid @ModelAttribute("viewModel") VirusCreateViewModel virusCreateViewModel,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            virusCreateViewModel.setTitle("Spread a new Virus");
            virusCreateViewModel.setAction("create");
            virusCreateViewModel.setAllCapitals(this.capitalService.getAllCapitalsNames());
            return this.view("viruses/add", virusCreateViewModel);
        } else{
            this.virusService.saveVirus(virusCreateViewModel.getVirusBindingModel());
            return this.redirect("/viruses");
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name="id")String id) {
        VirusBindingModel virusBindingModel = this.virusService.getVirus(id);

        VirusCreateViewModel viewModel = new VirusCreateViewModel(
                "Edit Virus!",
                "edit",
                this.capitalService.getAllCapitalsNames(),
                virusBindingModel
        );

        return this.view("viruses/add", viewModel);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editPost(
            @Valid @ModelAttribute VirusCreateViewModel virusCreateViewModel,
            BindingResult bindingResult,
            @PathVariable(name="id") String id
    ) {
        if(bindingResult.hasErrors()) {
            virusCreateViewModel.setTitle("Edit Virus");
            virusCreateViewModel.setAction("edit");
            virusCreateViewModel.setAllCapitals(this.capitalService.getAllCapitalsNames());

            return this.view("viruses/add", virusCreateViewModel);
        } else{
            this.virusService.updateVirus(id, virusCreateViewModel.getVirusBindingModel());

            return this.redirect("/viruses");
        }
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(name="id")String id) {
        VirusBindingModel virusBindingModel = this.virusService.getVirus(id);

        VirusCreateViewModel viewModel = new VirusCreateViewModel(
                "Edit Virus!",
                "delete",
                this.capitalService.getAllCapitalsNames(),
                virusBindingModel
        );

        return this.view("viruses/add", viewModel);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deletePost(@PathVariable(name="id") String id) {
        this.virusService.deleteVirus(id);
        return this.redirect("/viruses");
    }

    @GetMapping("")
    public ModelAndView showAll() {
        return super.view("viruses/all", this.virusService.getAllViruses());
    }
}
