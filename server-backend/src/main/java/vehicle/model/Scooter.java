package com.tecnico.lemon.vehicle.model;
import lombok.*;

@Data
@Builder
public class Scooter implements Vehicle {
    private int id;
    private String location;

}
