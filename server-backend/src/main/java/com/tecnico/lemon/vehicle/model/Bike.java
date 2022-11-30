package com.tecnico.lemon.vehicle.model;
import lombok.*;



public class Bike extends Vehicle {

    private final int _price = 50;

    public int get_price() {
        return _price;
    }

    public Bike(int id, String location, String title){
        super(id,location,title);

    }


}