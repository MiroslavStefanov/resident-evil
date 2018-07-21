package org.softuni.residentevil.models.view;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserViewModel {
    protected String id;
    protected String username;
    protected String email;
    protected String role;

    public UserViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Size(min = 1)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
