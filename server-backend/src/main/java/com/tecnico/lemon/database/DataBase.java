package com.tecnico.lemon.database;

import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.dtos.vehicle.VehicleDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataBase {

    VehicleTableServiceFrontend vehicleFrontend;
    UserTableServiceFrontend usersFrontend;
    int user_id;

    public DataBase() {
        vehicleFrontend = new VehicleTableServiceFrontend();
        usersFrontend = new UserTableServiceFrontend();
        user_id = 0;
    }

    public boolean saveUser(UserInfo userinfo) {
       return usersFrontend.saveUser(userinfo);
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

    public boolean lookupUser(String email) {
        return usersFrontend.lookupUser(email);
    }

    public void lockVehicle(int id){
        vehicleFrontend.lockVehicle(id);
    }

    public void unlockVehicle(int id){
        vehicleFrontend.unlockVehicle(id);
    }
}
