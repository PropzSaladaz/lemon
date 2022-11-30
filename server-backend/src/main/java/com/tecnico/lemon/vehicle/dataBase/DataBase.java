package com.tecnico.lemon.vehicle.dataBase;

import com.tecnico.lemon.vehicle.model.Bike;
import com.tecnico.lemon.vehicle.model.Scooter;
import com.tecnico.lemon.vehicle.model.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.*;



public class DataBase {
    private Map<Integer, Vehicle> _hm;
    private int _id;
    public DataBase(){
        _hm = new HashMap<Integer, Vehicle>();
        _id = 1;
    }

    public Map<Integer, Vehicle> get_hm() {
        return _hm;
    }

    public int get_id() {
        return _id;
    }
    public void incrementID(){
        _id+=1;
    }

    public void createScooter(String location){
        _hm.put(_id,new Scooter(_id,location,"Scooter"));
        incrementID();
    }

    public void createBike(String location){
        _hm.put(_id,new Bike(_id,location,"Bike"));
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
    }

}
