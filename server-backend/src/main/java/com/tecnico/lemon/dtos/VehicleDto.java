package com.tecnico.lemon.dtos;

import com.tecnico.lemon.models.vehicle.IVehicle;
import com.tecnico.lemon.models.vehicle.Vehicle;

public class VehicleDto {
    public int id;
    public String location;
    public String title;
    public int price;
    public Boolean locked;

    public VehicleDto(Vehicle vehicle) {
        id = vehicle.getId();
        location = vehicle.getLocation();
        title = vehicle.getTitle();
        price = vehicle.getPrice();
        locked = vehicle.getLocked();
    }

    public Boolean getLocked(){
        return locked;
    }
}
