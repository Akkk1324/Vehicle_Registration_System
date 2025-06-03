package com.vehicleregistration.service;
import java.util.List;
import com.vehicleregistration.model.*;

public interface RegistrationService {
    void registerVehicle(VehicleRegistrationDTO registrationDTO);
    VehicleDetailsDTO getVehicleDetails(String vin);
    CustomerVehiclesDTO getCustomerVehicles(Long customerId);
    List<VehicleDetailsDTO> searchVehicles(String searchTerm);
    void updateRegistration(RegistrationUpdateDTO updateDTO);
}