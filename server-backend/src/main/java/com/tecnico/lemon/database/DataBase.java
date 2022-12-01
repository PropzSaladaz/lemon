package com.tecnico.lemon.database;

import com.tecnico.lemon.models.vehicle.Bike;
import com.tecnico.lemon.models.vehicle.Scooter;
import com.tecnico.lemon.models.vehicle.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.*;



public class DataBase {
    private Map<Integer, Vehicle> _hm;
    private int _id;
    public DataBase(){
        _hm = new HashMap<Integer, Vehicle>();
        _id = 1;
        createBike("https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.046022212302!2d-9.3042988!3d38.7404982!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x92000ad2cef547fa!2sTaguspark!5e0!3m2!1spt-PT!2spt!4v1669853654934!5m2!1spt-PT!2spt");
        createBike("https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.687282539375!2d-9.138705!3d38.7368192!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x880c7c731a54423!2sInstituto%20Superior%20T%C3%A9cnico!5e0!3m2!1spt-PT!2spt!4v1669853788927!5m2!1spt-PT!2spt");
        createScooter("https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12444.431467602202!2d-9.1617956!3d38.7612299!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xc4fe8035597d8f22!2sEst%C3%A1dio%20Jos%C3%A9%20Alvalade!5e0!3m2!1spt-PT!2spt!4v1669853828021!5m2!1spt-PT!2spt");
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
    }

}
