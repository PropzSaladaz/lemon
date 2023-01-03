package com.tecnico.lemon.services;

import com.tecnico.lemon.dtos.vehicle.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {


    List<VehicleDto> getAvailableVehicles();

    List<VehicleDto> getReservedVehicles();

    void reserveVehicle(int id, String publicKey);
    void unlockVehicle(int id);

    //VehicleDto getVehicle(int id);

    /*void createScooter(String location);

    void createBike(String location);


    void removeVehicle(int id);

    void payVehicle(int id);*/
}
