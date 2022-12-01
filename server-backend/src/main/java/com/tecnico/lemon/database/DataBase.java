package com.tecnico.lemon.database;

import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.models.vehicle.Bike;
import com.tecnico.lemon.models.vehicle.Scooter;
import com.tecnico.lemon.models.vehicle.Vehicle;
import com.tecnico.lemon.services.VehicleServiceFrontend;


import lombok.Builder;
import lombok.Data;

import java.util.*;



public class DataBase {

    VehicleServiceFrontend vehicleFront;
    public DataBase(){
        vehicleFront =  new VehicleServiceFrontend();
    }

    public List<VehicleDto> getAvailableVehicles() {
        List<VehicleDto> list = vehicleFront.getVehicles();
        List<VehicleDto> listFinal = new ArrayList<VehicleDto>();
        for (VehicleDto vehicle : list) {
            if(!vehicle.getLocked()){
                listFinal.add(vehicle);
            }
        }
        return listFinal;
    }

    public List<VehicleDto> getLockedVehicles() {
        List<VehicleDto> list = vehicleFront.getVehicles();
        List<VehicleDto> listFinal = new ArrayList<VehicleDto>();
        for (VehicleDto vehicle : list) {
            if(vehicle.getLocked()){
                listFinal.add(vehicle);
            }
        }
        return listFinal;
    }

    public void lockVehicle(int id){
        vehicleFront.lockVehicle(id);
    }

    public void unlockVehicle(int id){
        vehicleFront.unlockVehicle(id);
    }
}