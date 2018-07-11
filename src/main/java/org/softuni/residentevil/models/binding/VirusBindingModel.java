package org.softuni.residentevil.models.binding;

import org.softuni.residentevil.core.validation.annotations.PastDate;
import org.softuni.residentevil.core.validation.annotations.VirusCreator;
import org.softuni.residentevil.models.entities.Magnitude;
import org.softuni.residentevil.models.entities.Mutation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class VirusBindingModel {
    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private Boolean isDeadly;
    private Boolean isCurable;
    private Mutation mutation;
    private Short turnoverRate;
    private Short hoursUntilTurn;
    private Magnitude magnitude;
    private LocalDate releasedOn;
    private Set<CapitalBindingViewModel> capitals;

    public VirusBindingModel() {
        this.capitals = new LinkedHashSet<>();
    }

    @NotNull
    @Size(min = 3, max = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Size(min = 5, max = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Size(max = 50)
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public Boolean getDeadly() {
        return isDeadly;
    }

    @VirusCreator
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setDeadly(Boolean deadly) {
        isDeadly = deadly;
    }

    public Boolean getCurable() {
        return isCurable;
    }

    public void setCurable(Boolean curable) {
        isCurable = curable;
    }

    @NotNull
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @Min(0)
    @Max(100)
    public Short getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Short turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Min(1)
    @Max(12)
    public Short getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Short hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull
    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @PastDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    public Set<CapitalBindingViewModel> getCapitals() {
        return this.capitals;
    }

    public void setCapitals(Set<Long> capitals) {
        this.capitals = capitals.stream().map(l -> new CapitalBindingViewModel(l, "")).collect(Collectors.toSet());
    }

    public void fillCapitalsName(Set<CapitalBindingViewModel> allCapitals) {
        this.capitals.forEach(
                capitalModel -> capitalModel.setName(
                        allCapitals
                                .stream()
                                .filter(
                                        c -> c
                                                .getId()
                                                .equals(capitalModel.getId()))
                                .findFirst()
                                .orElse(null)
                                .getName()
                )
        );
    }
}
