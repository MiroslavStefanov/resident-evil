package org.softuni.residentevil.models.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "capitals")
public class Capital {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Set<Virus> viruses;

    public Capital() {
        this.viruses = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @ManyToMany(mappedBy = "capitals")
    public Set<Virus> getViruses() {
        return Collections.unmodifiableSet(this.viruses);
    }

    public void setViruses(Set<Virus> viruses) {
        this.viruses = viruses;
    }
}
