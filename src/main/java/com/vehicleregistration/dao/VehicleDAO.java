package com.vehicleregistration.dao;

import com.vehicleregistration.model.Vehicle;
import java.util.List;

public interface VehicleDAO {
    void save(Vehicle vehicle);
    Vehicle findByVin(String vin);
    List<Vehicle> findAll();
    List<Vehicle> findByOwner(Long ownerId);
    void update(Vehicle vehicle);
    void delete(String vin);
}