package com.tecnico.lemon.dtos.vehicle;

import com.tecnico.lemon.contract.VehicleTableServiceOuterClass;
import com.tecnico.lemon.models.vehicle.Vehicle;

public class VehicleDto {
    public int id;
    public String location;
    public String description;
    public int price;
    public Boolean reserved;

    public VehicleDto(Vehicle vehicle) {
        id = vehicle.getId();
        location = vehicle.getLocation();
        description = vehicle.getDescription();
        price = vehicle.getPrice();
        reserved = vehicle.isReserved();
    }

    public VehicleDto(VehicleTableServiceOuterClass.Vehicle vehicle) {
        id = vehicle.getVehicleId();
        location = vehicle.getLocation();
        description = vehicle.getDescription();
        price = vehicle.getPrice();
        reserved = vehicle.getReserved();
    }

    public Boolean isReserved(){
        return reserved;
    }
}
