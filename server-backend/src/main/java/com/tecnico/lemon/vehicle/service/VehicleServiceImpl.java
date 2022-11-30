package com.tecnico.lemon.vehicle.service;

import com.tecnico.lemon.vehicle.model.Scooter;
import com.tecnico.lemon.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{

    private List<Scooter> scooters = List.of(
            Scooter.builder().id(5).build(),
            Scooter.builder().id(10).build()
    );

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.addAll(scooters);
        return vehicles;
    }
}
