package org.softuni.residentevil.services;

import org.softuni.residentevil.models.binding.VirusBindingModel;
import org.softuni.residentevil.models.view.VirusShowViewModel;

import java.util.Set;

public interface VirusService {
    boolean saveVirus(VirusBindingModel virusBindingModel);

    boolean updateVirus(String id, VirusBindingModel virusBindingModel);

    boolean deleteVirus(String id);

    Set<VirusShowViewModel> getAllViruses();

    VirusBindingModel getVirus(String id);

    String getAllMapViruses();
}
