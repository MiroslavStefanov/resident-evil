package org.softuni.residentevil.controllers;

import org.softuni.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController extends BaseController {
    private final VirusService virusService;

    @Autowired
    public MapController(VirusService virusService) {
        this.virusService = virusService;
    }

    @GetMapping("/map")
    public ModelAndView map() {
        return this.view("maps/map", virusService.getAllMapViruses(), true);
    }
}
