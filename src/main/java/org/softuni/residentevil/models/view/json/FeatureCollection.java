package org.softuni.residentevil.models.view.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeatureCollection implements Serializable {
    private String type;
    private List<Feature> features;

    public FeatureCollection() {
        this.type = "FeatureCollection";
        this.features = new ArrayList<>();
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
