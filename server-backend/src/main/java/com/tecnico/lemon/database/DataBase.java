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

    /*public List<VehicleDto> Vehicle() {
        List<VehicleDto> vehicles = getLockedVehicles();
        for (v in vehicles){
            (if)
        }
    }*/

    /*public int get_id() {
        return _id;
    }
    public void incrementID(){
        _id+=1;
    }

    public void createScooter(String location){
        _hm.put(_id,new Scooter(_id,location));
        incrementID();
    }

    public void createBike(String location){
        _hm.put(_id,new Bike(_id,location));
        incrementID();
    }

    public void removeVehicle(int id){
        _hm.remove(id);
    }

    public void lockVehicle(int id){
        _hm.get(id).lockVehicle();
    }

    public void unlockVehicle(int id){
        _hm.get(id).unlockVehicle();
    }

    public void payVehicle(int id){
        _hm.get(id).payVehicle();
    }*/

}
