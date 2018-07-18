package org.softuni.residentevil.models.view.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Feature implements Serializable {
    private String type;
    private Map<String, Object> properties;
    private GeometryFeature geometry;

    public Feature() {
        this.type = "Feature";
        this.properties = new HashMap<>();
        geometry = new GeometryFeature();
    }

    public Feature(double x, double y, double magnitude) {
        this.type = "Feature";
        this.properties = new HashMap<>(){{
            put("mag", magnitude);
            put("color", "#f00");
        }};
        this.geometry = new GeometryFeature(x, y);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public GeometryFeature getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryFeature geometry) {
        this.geometry = geometry;
    }
}
