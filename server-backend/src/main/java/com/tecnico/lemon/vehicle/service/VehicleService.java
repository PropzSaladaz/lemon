package com.tecnico.lemon.vehicle.service;

import com.tecnico.lemon.vehicle.dto.VehicleDto;
import com.tecnico.lemon.vehicle.model.Vehicle;
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
