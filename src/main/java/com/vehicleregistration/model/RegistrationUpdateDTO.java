package com.vehicleregistration.model;

import java.time.LocalDate;

public class RegistrationUpdateDTO {
    private String vin;
    private LocalDate newExpiryDate;

    // Getters and Setters
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public LocalDate getNewExpiryDate() { return newExpiryDate; }
    public void setNewExpiryDate(LocalDate newExpiryDate) { this.newExpiryDate = newExpiryDate; }
}