package com.abc.service;

import java.math.BigDecimal;

import com.abc.domain.FuelType;
import com.abc.domain.VehicleType;

public class ConfigDataServiceImpl implements ConfigDataService {

    private static final int DEFAULT = 5;
    private static final double PERCENTAGE_REDUCTION_FOR_BUSES = 2.0;
    private static final double AC_COST_PER_KM = 2.0;
    private static final double STANDARD_RATE_PETROL_VEHICLE = 15.00;
    private static final double STANDARD_RATE_DEISEL_VEHICLE = 14.00;


    public Double getDistanceBetweenDestinations(String source, String destination) {
        if(source.equals(destination)){
            return BigDecimal.ZERO.doubleValue();
        }
        return source.length()*100.00;
    }

    public Double getAcCostForKm() {
        return AC_COST_PER_KM;
    }

    public Double getBasicFareForVehicle(String fuelType) {
        
        if(FuelType.PETROL.toString().equals(fuelType)){
            return STANDARD_RATE_PETROL_VEHICLE;
        }
        return STANDARD_RATE_DEISEL_VEHICLE;
    }

    public Double getPercentageReductionForVehicleType(String vehicleType) {
       if(VehicleType.BUS.toString().equalsIgnoreCase(vehicleType)) {
           return PERCENTAGE_REDUCTION_FOR_BUSES;
       }
       return 0.0;
    }

    public int getMaxPassengerLimitForVehicle(String vehicleType, String model) {
        return DEFAULT;
    }
}
