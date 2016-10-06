package com.abc.service;

import java.math.BigDecimal;

public class CarRentalServiceImpl implements CarRentalService{
    
    private final ConfigDataService configDataService;
    
    public CarRentalServiceImpl(ConfigDataService configDataService) {
        this.configDataService = configDataService;
    }
    
    public Double calulateTripCost(String vehicleType, String model, boolean hasAirCondition, String fuelType, int noOfPassengers, String route) {
        if( vehicleType == null || model == null || fuelType == null || route == null) {
            throw new IllegalArgumentException("car's model, car's fuel type and route of the trip mandatory arguments");
        }
        if(noOfPassengers < 1){
            throw new IllegalArgumentException("no of passengers must be atleast 1");
        }
        String[] destinations = route.split("-"); 
        if(destinations == null || destinations.length <2) {
            throw new IllegalArgumentException("A trip should atleast contain one journey");
        }
        Double totalDistanceForRoute = getTotalDistanceForRoute(destinations);
        Double farePerKm = calculateFareForKm(vehicleType, fuelType, hasAirCondition);
        Double totalCostOfTrip = totalDistanceForRoute * farePerKm;
        totalCostOfTrip += getExtraPassengerPenaltyCost(vehicleType, model, noOfPassengers, totalDistanceForRoute);
        return totalCostOfTrip;
    }

    private Double getExtraPassengerPenaltyCost(String vehicleType, String model, 
                    int noOfPassengers, Double totalDistanceForRoute) {
        int maxPassengerLimit = configDataService.getMaxPassengerLimitForVehicle(vehicleType, model);
        if(noOfPassengers > maxPassengerLimit){
            int extraPassengers = noOfPassengers -maxPassengerLimit;
            return extraPassengers * totalDistanceForRoute;
        }
        return 0.0;
    }

    private Double getTotalDistanceForRoute(String[] destinations) {
        Double totalDistance = 0.0;
        for (int i = 0, j=1; j < destinations.length; i++, j++) {
            totalDistance += configDataService.getDistanceBetweenDestinations(
                            destinations[i], destinations[j]);
        }
        return totalDistance;
    }
    
    private Double calculateFareForKm(String vehicleType,String fuelType,  boolean hasAirCondition) {
        Double farePerKm = configDataService.getBasicFareForVehicle(fuelType);
        if(hasAirCondition) {
            farePerKm = applyACTariff(farePerKm);
        }
        farePerKm = applyReductionOnBasicFareGivenVehicleType(vehicleType, farePerKm);
        return farePerKm;
    }

    private Double applyReductionOnBasicFareGivenVehicleType(String vehicleType, Double costForKm) {
        Double percentageReduction = configDataService.getPercentageReductionForVehicleType(vehicleType);
        if(percentageReduction > 0){
            return costForKm - (costForKm*percentageReduction*0.01);
        }
        return costForKm;
    }
    
    private Double applyACTariff(Double basicCostForKm) {
        return basicCostForKm + configDataService.getAcCostForKm();
    }

}
