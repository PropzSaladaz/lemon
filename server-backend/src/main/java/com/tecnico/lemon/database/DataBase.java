package com.tecnico.lemon.database;

import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.dtos.UserInfoDto;
import com.tecnico.lemon.models.vehicle.Bike;
import com.tecnico.lemon.models.vehicle.Scooter;
import com.tecnico.lemon.models.vehicle.Vehicle;
import com.tecnico.lemon.models.user.UserForm;

import lombok.Builder;
import lombok.Data;

import java.util.*;



public class DataBase {

    VehicleTableServiceFrontend vehicleFrontend;
    UserTableServiceFrontend usersFrontend;
    int user_id;

    public DataBase() {
        vehicleFrontend = new VehicleTableServiceFrontend();
        usersFrontend = new UserTableServiceFrontend();
        user_id = 0;
    }

    public void createUser(UserForm userForm) {
        usersFrontend.createUser(userForm, user_id++);
    }

    public UserInfoDto lookupUser(String email) {
        return usersFrontend.lookupUser(email);
    }

    public List<VehicleDto> getAvailableVehicles() {
        List<VehicleDto> list = vehicleFrontend.getVehicles();
        List<VehicleDto> listFinal = new ArrayList<VehicleDto>();
        for (VehicleDto vehicle : list) {
            if(!vehicle.getLocked()){
                listFinal.add(vehicle);
            }
        }
        return listFinal;
    }

    public List<VehicleDto> getLockedVehicles() {
        List<VehicleDto> list = vehicleFrontend.getVehicles();
        List<VehicleDto> listFinal = new ArrayList<VehicleDto>();
        for (VehicleDto vehicle : list) {
            if(vehicle.getLocked()){
                listFinal.add(vehicle);
            }
        }
        return listFinal;
    }

    public void lockVehicle(int id){
        vehicleFrontend.lockVehicle(id);
    }

    public void unlockVehicle(int id){
        vehicleFrontend.unlockVehicle(id);
    }
}
