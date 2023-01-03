package com.tecnico.lemon.models.vehicle;

public class Bike extends Vehicle {

    private final static int _price = 50;
    private final static String _description = "Bike";

    public int get_price() {
        return _price;
    }
    public Bike(int id, String location, boolean reserved){
        super(id, location, _price, reserved, _description);
    }


}