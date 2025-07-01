package com.vehicleregistration.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @com.google.gson.annotations.Expose
    private Long id;
    
    @Column(nullable = false, length = 50)
    @com.google.gson.annotations.Expose
    private String firstName;
    
    @Column(nullable = false, length = 50)
    @com.google.gson.annotations.Expose
    private String lastName;
    
    @Column(nullable = false, unique = true, length = 100)
    @com.google.gson.annotations.Expose
    private String email;
    
    @Column(length = 20)
    @com.google.gson.annotations.Expose
    private String phone;
    
    @Column(length = 200)
    @com.google.gson.annotations.Expose
    private String address;
    
    @Column(length = 50)
    @com.google.gson.annotations.Expose
    private String city;
    
    @Column(length = 50)
    @com.google.gson.annotations.Expose
    private String state;
    
    @Column(length = 10)
    @com.google.gson.annotations.Expose
    private String zipCode;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.google.gson.annotations.Expose(serialize = false)
    private List<Registration> registrations = new ArrayList<>();
    
    // Constructors
    public Customer() {}
    
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
    
    public void addRegistration(Registration registration) {
        registrations.add(registration);
        registration.setCustomer(this);
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
