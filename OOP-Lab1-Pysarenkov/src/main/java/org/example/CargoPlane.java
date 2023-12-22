package org.example;

public class CargoPlane extends Plane {
    private double maxCargoCapacity;

    public CargoPlane(final int id, final String modelName,
                      final int range, final double fuelConsumption, final double maxCargoCapacity) {
        super(id, modelName, range, fuelConsumption);
        this.maxCargoCapacity = maxCargoCapacity;
    }

    @Override
    public double getMaxCargoCapacity() {
        return maxCargoCapacity;
    }

    @Override
    public int getSeats() {
        return 0;
    }
}
