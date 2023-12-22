package org.example;

public class PassengerPlane extends Plane {
    private final int seats;

    public PassengerPlane(final int id, final String modelName, final int range, final double fuelConsumption, final int seats) {
        super(id, modelName, range, fuelConsumption);
        this.seats = seats;
    }

    @Override
    public double getMaxCargoCapacity() {
        return 0.;
    }

    @Override
    public int getSeats() {
        return seats;
    }
}
