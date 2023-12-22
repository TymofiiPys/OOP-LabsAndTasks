package org.example;

import java.util.ArrayList;

abstract class Plane {
    private final int id;
    private final String modelName;
    private final int range;
    private final double fuelConsumption;

    public Plane(final int id, final String modelName, final int range, final double fuelConsumption) {
        this.id = id;
        this.modelName = modelName;
        this.range = range;
        this.fuelConsumption = fuelConsumption;
    }

    public int getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    abstract double getMaxCargoCapacity();

    abstract int getSeats();

    public int getRange() {
        return range;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }
}
