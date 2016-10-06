package com.abc.service;

import org.junit.Test;

import com.abc.domain.FuelType;
import com.abc.domain.VehicleType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CarRentalServiceImplTest {
    
    private ConfigDataService configDataService = new ConfigDataServiceImpl();
    private CarRentalService underTest = new CarRentalServiceImpl(configDataService );

    @Test (expected = IllegalArgumentException.class)
    public void testCalulateTripCostThrowsExceptionWhenMandatoryArgumentsAreMissing() {
        underTest.calulateTripCost(VehicleType.CAR.toString(),null, Boolean.TRUE, FuelType.DIESEL.toString(), 1, null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testCalulateTripCostThrowsExceptionWhenNoOfPassengersLessThanOne() {
        underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", Boolean.TRUE, FuelType.DIESEL.toString(), 0, "Mumbai-Bangalore-Mumbai");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testCalulateTripCostThrowsExceptionWhenRouteIsInValidFormat() {
        underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", Boolean.TRUE, FuelType.DIESEL.toString(), 1, "MumbaiMumbai");
    }
    
    @Test 
    public void testCalculateTripCostReturnsCostWhenFuelTypeIsPetrol(){
        Double totalTripCost = underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", 
                        Boolean.FALSE, FuelType.PETROL.toString(), 1, "Mumbai-Bangalore");
        assertNotNull(totalTripCost);
        assertEquals(9000.00, totalTripCost.doubleValue(),0.0);
    }

    @Test 
    public void testCalculateTripCostReturnsCostWhenFuelTypeIsDeisel(){
        Double totalTripCost = underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", 
                        Boolean.FALSE, FuelType.DIESEL.toString(), 1, "Mumbai-Bangalore");
        assertNotNull(totalTripCost);
        assertEquals(8400.00, totalTripCost.doubleValue(),0.0);
    }
    
    @Test 
    public void testCalculateTripCostReturnsCostForACVehicle(){
        Double totalTripCost = underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", 
                        Boolean.TRUE, FuelType.PETROL.toString(), 1, "Mumbai-Bangalore");
        assertNotNull(totalTripCost);
        assertEquals(10200.00, totalTripCost.doubleValue(),0.0);
    }
    
    @Test 
    public void testCalculateTripCostReturnsCostWhenRouteHasMultipleDestinations(){
        Double totalTripCost = underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", 
                        Boolean.FALSE, FuelType.PETROL.toString(), 1, "Mumbai-Bangalore-Delhi");
        assertNotNull(totalTripCost);
        assertEquals(22500.00, totalTripCost.doubleValue(),0.0);
    }
    
    @Test 
    public void testCalculateTripCostReturnsCostWhenVehicleTypeReductionIsApplied(){
        Double totalTripCost = underTest.calulateTripCost(VehicleType.BUS.toString(),"Swift", 
                        Boolean.FALSE, FuelType.PETROL.toString(), 1, "Mumbai-Bangalore");
        assertNotNull(totalTripCost);
        assertEquals(8820.00, totalTripCost.doubleValue(),0.0);
    }
    
    @Test 
    public void testCalculateTripCostAddsCostWhenNoOfPassengersIsMoreThanAllowedLimit(){
        Double totalTripCost = underTest.calulateTripCost(VehicleType.CAR.toString(),"Swift", 
                        Boolean.FALSE, FuelType.PETROL.toString(), 7, "Mumbai-Bangalore");
       assertNotNull(totalTripCost);
        assertEquals(10200.00, totalTripCost.doubleValue(),0.0);
        
    }
}
