package com.tecnico.lemon.controllers;

import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.models.vehicle.Vehicle;
import com.tecnico.lemon.services.VehicleService;
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
    public List<VehicleDto> getAvailableVehicles() {
        return vehicleService.getAvailableVehicles();
    }

    @GetMapping(value="/locked")
    public List<VehicleDto> getLockedVehicles() {
        return vehicleService.getLockedVehicles();
    }


    @PostMapping (value="/lock/{id}")
    public void lockVehicle(@PathVariable("id") int id) {
        System.out.println(id);
        vehicleService.lockVehicle(id);
    }

    @PostMapping (value="/unlock/{id}")
    public void unlockVehicle(@PathVariable("id") int id) {
        vehicleService.unlockVehicle(id);
    }

    /*@GetMapping(value="/get/{id}")
    public VehicleDto getVehicle(@PathVariable("id") int id) {
       return vehicleService.getVehicle(id);*/
    }

     /*@PutMapping (value="/bike/{loc}")
    public void createBike(@PathVariable("loc") String loc) {
        vehicleService.createBike(loc);
    }

   @PutMapping (value="/scooter/{location}")
    public void createScooter(@PathVariable("location") String loc) {
        vehicleService.createScooter(loc);
    }

    @PutMapping (value="/remove/{id}")
    public void removeVehicle(@PathVariable("id") int id) {
        vehicleService.removeVehicle(id);
    }*/

//    @PutMapping (value="/pay/{id}")
//    public void payVehicle(@PathVariable("id") int id) {
//        vehicleService.payVehicle(id);
//    }






