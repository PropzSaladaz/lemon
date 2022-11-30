package com.tecnico.lemon.vehicle.service;

import com.tecnico.lemon.vehicle.dataBase.DataBase;
import com.tecnico.lemon.vehicle.dto.VehicleDto;
import com.tecnico.lemon.vehicle.model.Scooter;
import com.tecnico.lemon.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService{

    private DataBase dBase = new DataBase();


    @Override
    public List<VehicleDto> getAvailableVehicles() {
        Vehicle vec;
        List<VehicleDto> vehicles = new ArrayList<VehicleDto>();
        Map<Integer, Vehicle> map = dBase.get_hm();
        for (Map.Entry<Integer,Vehicle> entry : map.entrySet()){
            vec = entry.getValue();
            if (vec.isFree()) {
                vehicles.add(new VehicleDto(entry.getValue()));
            }
        }
        return vehicles;

    }

    @Override
    public VehicleDto getVehicle(int id) {
        return new VehicleDto(dBase.get_hm().get(id));
    }

    @Override
    public void createScooter(String location) {
        dBase.createScooter(location);
    }

    @Override
    public void createBike(String location) {
        dBase.createBike(location);
    }

    @Override
    public void removeVehicle(int id){
        dBase.removeVehicle(id);
    }
    @Override
    public void lockVehicle(int id){
        dBase.lockVehicle(id);
    }
    @Override
    public void unlockVehicle(int id){
        dBase.unlockVehicle(id);
    }
    @Override
    public void payVehicle(int id){
        dBase.payVehicle(id);
    }


}
