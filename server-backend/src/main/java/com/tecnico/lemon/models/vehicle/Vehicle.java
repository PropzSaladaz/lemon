package com.tecnico.lemon.models.vehicle;

public class Vehicle implements IVehicle{
    private int _id;
    private String _location;
    private int _price;
    private boolean _paid;
    public boolean _reserved;
    private String _description;

    public Vehicle(int id, String location, int price, boolean reserved, String description){
        _id = id;
        _location = location;
        _price = price;
        _reserved = reserved;
        _description = description;
        _paid = false;
    }


    public int getId() {
        return _id;
    }
    public String getLocation(){
        return _location;
    }

    public int getPrice(){
        return _price;
    }

    public String getDescription(){
        return _description;
    }

    public Boolean isReserved(){
        return _reserved;
    }

    public void unlockVehicle() {
        if (_reserved) _reserved = false;

    }

    public void reserveVehicle() {
        if (!_reserved) _reserved = true;

    }

    public String payVehicle()
    {
        if (_paid) return "Already payed";
        else {
            _paid = true;
            return "Payment Realized";
        }

    }


}
