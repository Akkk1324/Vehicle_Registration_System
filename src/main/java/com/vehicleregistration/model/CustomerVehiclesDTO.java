package com.vehicleregistration.model;

import java.util.List;

public class CustomerVehiclesDTO {
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private List<VehicleSummaryDTO> vehicles;

    public CustomerVehiclesDTO(Long customerId, String customerName, String customerEmail, 
                             List<VehicleSummaryDTO> vehicles) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicles = vehicles;
    }

    // Getters
    public Long getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public List<VehicleSummaryDTO> getVehicles() { return vehicles; }
}