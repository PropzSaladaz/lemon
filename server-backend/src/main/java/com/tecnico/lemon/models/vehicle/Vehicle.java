package com.tecnico.lemon.models.vehicle;

import lombok.Builder;


public class Vehicle implements IVehicle{
    private int _id;
    private int _price;
    private String _location;
    private  boolean _payed;
    public boolean _locked;

    private String _title;

    public Vehicle(int id,String location,String title, int price){
        _id = id;
        _location = location ;
        _title = title;
        _locked = false;
        _payed = false;
        _price = price;
    }


    public int getId() {
        return _id;
    }
    public String getLocation(){
        return _location;
    }

    public boolean isFree() {
        return !_locked;
    }

    public int getPrice(){
        return _price;
    }

    public String getTitle(){
        return _title;
    }

    public void unlockVehicle()
    {
        if (_locked) _locked = false;

    }

    public void lockVehicle()
    {
        if (!_locked) _locked = true;

    }

    public String payVehicle()
    {
        if (_payed) return "Already payed";
        else {
            _payed = true;
            return "Payment Realized";
        }

    }


}
