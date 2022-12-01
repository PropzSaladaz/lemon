package com.tecnico.lemon.services;

import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.models.vehicle.Scooter;
import com.tecnico.lemon.models.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        /*List<VehicleDto> lista = dBase.getAvailableVehicles();
        for ()*/
    }
    @Override
    public void unlockVehicle(int id){
        //dBase.unlockVehicle(id);
    }

    /*@Override
    public VehicleDto getVehicle(int id) {
        return new VehicleDto(dBase.get_hm().get(id));
    }*/

    /*@Override
    public void createScooter(String location) {
        dBase.createScooter(location);
    }*/

    /*@Override
    public void createBike(String location) {
        dBase.createBike(location);
    }
      @Override
    public void payVehicle(int id){
        dBase.payVehicle(id);
    }

    @Override
    public void removeVehicle(int id){
        dBase.removeVehicle(id);
    }*/



}
