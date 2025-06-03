package com.vehicleregistration.dao;

import com.vehicleregistration.model.Registration;
import java.util.List;

public interface RegistrationDAO {
    void save(Registration registration);
    Registration findById(Long id);
    Registration findByRegistrationNumber(String registrationNumber);
    Registration findByVehicleVin(String vin);
    List<Registration> findAll();
    void update(Registration registration);
    void delete(Long id);
}