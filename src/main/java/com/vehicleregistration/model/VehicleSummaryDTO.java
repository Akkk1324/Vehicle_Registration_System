package com.vehicleregistration.model;

import java.time.LocalDate;

public class VehicleSummaryDTO {
    private String vin;
    private String make;
    private String model;
    private int year;
    private String registrationNumber;
    private LocalDate expiryDate;

    public VehicleSummaryDTO(String vin, String make, String model, int year, 
                           String registrationNumber, LocalDate expiryDate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.expiryDate = expiryDate;
    }

    // Getters
    public String getVin() { return vin; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getRegistrationNumber() { return registrationNumber; }
    public LocalDate getExpiryDate() { return expiryDate; }
}