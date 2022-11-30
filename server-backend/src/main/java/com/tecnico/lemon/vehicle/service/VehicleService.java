package com.tecnico.lemon.vehicle.service;

import com.tecnico.lemon.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    void createScooter(String location);

    void createBike(String location);

    Vehicle getVehicle(int id);

    List<Vehicle> getVehicles();

    void removeVehicle(int id);
    void lockVehicle(int id);
    void unlockVehicle(int id);
    void payVehicle(int id);
}
