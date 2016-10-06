package com.abc.service;

public interface CarRentalService {
    
    Double calulateTripCost(String vehicleType, String model, boolean hasAirCondition, String fuelType, int noOfPassengers, String route);

}
