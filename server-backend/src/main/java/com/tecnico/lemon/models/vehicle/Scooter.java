package com.tecnico.lemon.models.vehicle;

public class Scooter extends Vehicle {

    private final static int _price = 200;
    private final static String _description = "Scooter";

    public Scooter(int id, String location, boolean reserved){
        super(id,location, _price, reserved, _description);
    }

    public int get_price() {
        return _price;
    }
}
