package com.tecnico.lemon.vehicle.controller;

import com.tecnico.lemon.vehicle.model.Vehicle;
import com.tecnico.lemon.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping()
    public List<Vehicle> getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping(value="/Get/{id}")
    public Vehicle getVehicle(@PathVariable("id") int id) {
       return vehicleService.getVehicle(id);
    }

    @PutMapping (value="/Bike/{loc}")
    public void createBike(@PathVariable("loc") String loc) {
        vehicleService.createBike(loc);
    }

    @PutMapping (value="/Scooter/{location}")
    public void createScooter(@PathVariable("location") String loc) {
        vehicleService.createScooter(loc);
    }

    @PutMapping (value="/Remove/{id}")
    public void removeVehicle(@PathVariable("id") int id) {
        vehicleService.removeVehicle(id);
    }

    @PutMapping (value="/Lock/{id}")
    public void lockVehicle(@PathVariable("id") int id) {
        System.out.println(id);
        vehicleService.lockVehicle(id);
    }

    @PutMapping (value="/Unlock/{id}")
    public void unlockVehicle(@PathVariable("id") int id) {
        vehicleService.unlockVehicle(id);
    }

    @PutMapping (value="/Pay/{id}")
    public void payVehicle(@PathVariable("id") int id) {
        vehicleService.payVehicle(id);
    }



}



