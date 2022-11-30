package com.tecnico.lemon.vehicle.model;
import lombok.*;



public class Scooter extends Vehicle {

    private final int _price = 200;

    public Scooter(int id,String location,String title){
        super(id,location,title);

    }

    public int get_price() {
        return _price;
    }
}
