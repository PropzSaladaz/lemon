package com.tecnico.lemon.models.vehicle;
import lombok.*;



public class Scooter extends Vehicle {

    private final static int _price = 200;
    private final static String _title = "Scooter";

    public Scooter(int id,String location){
        super(id,location, _title, _price);

    }

    public int get_price() {
        return _price;
    }
}
