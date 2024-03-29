package com.tecnico.lemon.controllers;

import com.tecnico.lemon.dtos.vehicle.VehicleDto;
import com.tecnico.lemon.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

@RestController
@RequestMapping(value="/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping(value="/all")
    public List<VehicleDto> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
    @GetMapping()
    public List<VehicleDto> getAvailableVehicles() {
        return vehicleService.getAvailableVehicles();
    }

    @PostMapping(value="/reserved")
    public List<VehicleDto> getReservedVehicles(@RequestBody KeyRequest request) {
        return vehicleService.getUserReservedVehicles(request.getKey());
    }


    @PostMapping (value="/reserve/{id}")
    public void reserveVehicle(@PathVariable("id") int id, @RequestBody KeyRequest request) {
        vehicleService.reserveVehicle(id, request.getKey());
    }

    @PostMapping (value="/unlock/{id}")
    public void unlockVehicle(@PathVariable("id") int id) {
        vehicleService.unlockVehicle(id);
    }

    public static class KeyRequest {
        private String key;
        @JsonCreator
        public KeyRequest() { } // Default constructor
        @JsonCreator
        public KeyRequest(String _key) {this.key = _key;}
        public String getKey() { return key; }
        public void setKey(String _key) { this.key = _key; }
    }
}
    /*@GetMapping(value="/get/{id}")
    public VehicleDto getVehicle(@PathVariable("id") int id) {
       return vehicleService.getVehicle(id);*/

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







