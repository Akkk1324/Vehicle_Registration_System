package com.vehicleregistration.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @com.google.gson.annotations.Expose
    private Long id;
    
    @Column(nullable = false, unique = true, length = 17)
    @com.google.gson.annotations.Expose
    private String vin;
    
    @Column(nullable = false, length = 50)
    @com.google.gson.annotations.Expose
    private String make;
    
    @Column(nullable = false, length = 50)
    @com.google.gson.annotations.Expose
    private String model;
    
    @Column(nullable = false)
    @com.google.gson.annotations.Expose
    private Integer year;
    
    @Column(length = 30)
    @com.google.gson.annotations.Expose
    private String color;
    
    @Column(length = 50)
    @com.google.gson.annotations.Expose
    private String vehicleType;
    
    @Column(length = 30)
    @com.google.gson.annotations.Expose
    private String fuelType;
    
    @Column(length = 20)
    @com.google.gson.annotations.Expose
    private String engineSize;
    
    @Column(length = 20)
    @com.google.gson.annotations.Expose
    private String transmission;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.google.gson.annotations.Expose(serialize = false)
    private List<Registration> registrations = new ArrayList<>();
    
    // Constructors
    public Vehicle() {}
    
    public Vehicle(String vin, String make, String model, Integer year) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    
    public String getEngineSize() { return engineSize; }
    public void setEngineSize(String engineSize) { this.engineSize = engineSize; }
    
    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
    
    public void addRegistration(Registration registration) {
        registrations.add(registration);
        registration.setVehicle(this);
    }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
