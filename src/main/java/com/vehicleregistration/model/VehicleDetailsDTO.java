package com.vehicleregistration.model;

import java.time.LocalDate;

public class VehicleDetailsDTO {
    private String vin;
    private String make;
    private String model;
    private int year;
    private String color;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String registrationNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    public VehicleDetailsDTO(String vin, String make, String model, int year, String color, 
                           String ownerName, String ownerEmail, String ownerPhone, 
                           String registrationNumber, LocalDate issueDate, LocalDate expiryDate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPhone = ownerPhone;
        this.registrationNumber = registrationNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    // Getters
    public String getVin() { return vin; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getColor() { return color; }
    public String getOwnerName() { return ownerName; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getOwnerPhone() { return ownerPhone; }
    public String getRegistrationNumber() { return registrationNumber; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
}