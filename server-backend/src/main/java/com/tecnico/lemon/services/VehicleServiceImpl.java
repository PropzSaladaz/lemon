package com.tecnico.lemon.services;

import com.tecnico.lemon.database.VehicleTableServiceFrontend;
import com.tecnico.lemon.dtos.vehicle.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService{

    private VehicleTableServiceFrontend vehicleServiceFrontend = new VehicleTableServiceFrontend();

    @Override
    public List<VehicleDto> getAvailableVehicles() {
        return vehicleServiceFrontend.getVehicles().stream().filter(vehicle -> !vehicle.isReserved()).collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> getReservedVehicles() {
        return vehicleServiceFrontend.getVehicles().stream().filter(vehicle -> vehicle.isReserved()).collect(Collectors.toList());
    }

    @Override
    public void reserveVehicle(int id){
        vehicleServiceFrontend.reserveVehicle(id);
    }
    @Override
    public void unlockVehicle(int id){
        vehicleServiceFrontend.unlockVehicle(id);
    }
}
