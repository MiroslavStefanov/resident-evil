package org.softuni.residentevil.services;

import org.softuni.residentevil.models.binding.CapitalBindingViewModel;

import java.util.Set;

public interface CapitalService {
    Set<CapitalBindingViewModel> getAll();
}
