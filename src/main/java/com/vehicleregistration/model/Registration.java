package com.vehicleregistration.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @com.google.gson.annotations.Expose
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    @com.google.gson.annotations.Expose
    private String registrationNumber;
    
    @Column(nullable = false, unique = true, length = 15)
    @com.google.gson.annotations.Expose
    private String licensePlate;
    
    @Column(nullable = false)
    @com.google.gson.annotations.Expose
    private LocalDate registrationDate;
    
    @Column(nullable = false)
    @com.google.gson.annotations.Expose
    private LocalDate expirationDate;
    
    @Column(length = 20)
    @com.google.gson.annotations.Expose
    private String status;
    
    @Column
    @com.google.gson.annotations.Expose
    private Double registrationFee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @com.google.gson.annotations.Expose
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @com.google.gson.annotations.Expose
    private Vehicle vehicle;
    
    // Constructors
    public Registration() {}
    
    public Registration(String registrationNumber, String licensePlate, Customer customer, Vehicle vehicle) {
        this.registrationNumber = registrationNumber;
        this.licensePlate = licensePlate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.registrationDate = LocalDate.now();
        this.expirationDate = LocalDate.now().plusYears(1);
        this.status = "ACTIVE";
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    
    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Double getRegistrationFee() { return registrationFee; }
    public void setRegistrationFee(Double registrationFee) { this.registrationFee = registrationFee; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    
    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
