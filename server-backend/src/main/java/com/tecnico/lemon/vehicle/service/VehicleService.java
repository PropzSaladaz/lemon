package com.tecnico.lemon.vehicle.service;

import com.tecnico.lemon.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {
    List<Vehicle> getVehicles();
}
