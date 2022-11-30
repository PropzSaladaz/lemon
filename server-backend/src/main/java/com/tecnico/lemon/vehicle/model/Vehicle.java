package com.tecnico.lemon.vehicle.model;


import lombok.Builder;


public class Vehicle implements IVehicle{
    private int _id;
    private String _location;
    private  boolean _payed;
    public boolean _lockState;

    private String _title;

    public Vehicle(int id,String location,String title){
        _id = id;
        _location = location ;
        _title = title;
        _lockState = false;
        _payed = false;
    }


    public int get_id() {
        return _id;
    }

    public void unlockVehicle()
    {
        if (_lockState) _lockState = false;

    }

    public void lockVehicle()
    {
        if (!_lockState) _lockState = true;

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
