package org.softuni.residentevil.models.binding;

import org.softuni.residentevil.models.view.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserEditBindingModel extends UserViewModel {
    protected List<String> allRoles;

    public UserEditBindingModel() {
        this.allRoles = new ArrayList<>();
    }

    public List<String> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<String> allRoles) {
        this.allRoles = allRoles;
    }
}
