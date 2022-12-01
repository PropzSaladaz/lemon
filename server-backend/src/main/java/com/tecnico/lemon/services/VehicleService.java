package com.tecnico.lemon.services;

import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.models.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    void createScooter(String location);

    void createBike(String location);

    VehicleDto getVehicle(int id);

    List<VehicleDto> getAvailableVehicles();

    List<VehicleDto> getLockedVehicles();

    void removeVehicle(int id);
    void lockVehicle(int id);
    void unlockVehicle(int id);
    void payVehicle(int id);
}
