package com.tecnico.lemon.vehicle.controller;

import com.tecnico.lemon.vehicle.model.Vehicle;
import com.tecnico.lemon.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping()
    public List<Vehicle> getVehicles(String id) {
        return vehicleService.getVehicles();
    }

    @GetMapping(value="{id}")
    public void getVehicle(@PathVariable("id") String id) {
        // service.getVehicle(id)
    }
}
