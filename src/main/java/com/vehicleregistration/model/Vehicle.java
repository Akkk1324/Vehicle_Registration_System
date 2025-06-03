package com.vehicleregistration.model;

import jakarta.persistence.*;
import java.time.Year;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @Column(unique = true, nullable = false)
    private String vin; // Vehicle Identification Number
    
    @Column(nullable = false)
    private String make;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private Year year;
    
    @Column(nullable = false)
    private String color;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Customer owner;
    
    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private Registration registration;
    
    // Getters and Setters
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Year getYear() { return year; }
    public void setYear(Year year) { this.year = year; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Customer getOwner() { return owner; }
    public void setOwner(Customer owner) { this.owner = owner; }
    public Registration getRegistration() { return registration; }
    public void setRegistration(Registration registration) { this.registration = registration; }
}