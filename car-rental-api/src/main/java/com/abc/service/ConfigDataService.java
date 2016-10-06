package com.abc.service;

public interface ConfigDataService {
    
    Double getBasicFareForVehicle(String fuelType);
    
    Double getAcCostForKm();
    
    Double getPercentageReductionForVehicleType(String vehicleType);
    
    Double getDistanceBetweenDestinations(String source, String destination);

    int getMaxPassengerLimitForVehicle(String vehicleType, String model);
}
