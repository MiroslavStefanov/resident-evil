package org.softuni.residentevil.models.view;

import org.softuni.residentevil.models.binding.VirusBindingModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class VirusCreateViewModel {
    private String title;

    private String action;

    private List<String> allCapitals;

    private VirusBindingModel virusBindingModel;

    public VirusCreateViewModel() {
        this.allCapitals = new ArrayList<>();
        this.virusBindingModel = new VirusBindingModel();
    }

    public VirusCreateViewModel(String title, String action) {
        this.setTitle(title);
        this.setAction(action);
        this.allCapitals = new ArrayList<>();
        this.virusBindingModel = new VirusBindingModel();
    }

    public VirusCreateViewModel(
            String title,
            String action,
            @NotNull List<String> allCapitals,
            @NotNull VirusBindingModel virusBindingModel
    ) {
        this.setTitle(title);
        this.setAction(action);
        this.setAllCapitals(allCapitals);
        this.setVirusBindingModel(virusBindingModel);
    }

    public VirusCreateViewModel(String title, String action, @NotNull List<String> allCapitals) {
        this.setTitle(title);
        this.setAction(action);
        this.allCapitals = allCapitals;
        this.virusBindingModel = new VirusBindingModel();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @NotNull
    public List<String> getAllCapitals() {
        return allCapitals;
    }

    public void setAllCapitals(List<String> allCapitals) {
        this.allCapitals = allCapitals;
    }

    @NotNull
    @Valid
    public VirusBindingModel getVirusBindingModel() {
        return virusBindingModel;
    }

    public void setVirusBindingModel(VirusBindingModel virusBindingModel) {
        this.virusBindingModel = virusBindingModel;
    }
}
