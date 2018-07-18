package org.softuni.residentevil.models.view.json;

import java.io.Serializable;

public class GeometryFeature implements Serializable {
    private String type;
    private double[] coordinates;

    public GeometryFeature() {
        this.type = "Point";
        this.coordinates = new double[]{0.0, 0.0};
    }

    public GeometryFeature(double x, double y) {
        this.type = "Point";
        this.coordinates = new double[]{x, y};
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
