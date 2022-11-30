package com.tecnico.lemon.vehicle.dto;

import com.tecnico.lemon.vehicle.model.IVehicle;
import com.tecnico.lemon.vehicle.model.Vehicle;

public class VehicleDto {
    public int id;
    public String location;
    public String title;
    public int price;

    public VehicleDto(Vehicle vehicle) {
        id = vehicle.getId();
        location = vehicle.getLocation();
        title = vehicle.getTitle();
        price = vehicle.getPrice();
    }
}
