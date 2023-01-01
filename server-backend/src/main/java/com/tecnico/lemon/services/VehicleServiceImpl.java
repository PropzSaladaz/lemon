package com.tecnico.lemon.services;

import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.vehicle.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{

    private DataBase dBase = new DataBase();

    @Override
    public List<VehicleDto> getAvailableVehicles() {
        return dBase.getAvailableVehicles();


    }
    @Override
    public List<VehicleDto> getLockedVehicles() {
        return dBase.getLockedVehicles();

    }

    @Override
    public void lockVehicle(int id){
        dBase.lockVehicle(id);
    }
    @Override
    public void unlockVehicle(int id){
        dBase.unlockVehicle(id);
    }
}
