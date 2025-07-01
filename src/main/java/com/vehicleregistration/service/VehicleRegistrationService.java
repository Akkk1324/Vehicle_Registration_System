package com.vehicleregistration.service;

import com.vehicleregistration.dao.CustomerDAO;
import com.vehicleregistration.dao.VehicleDAO;
import com.vehicleregistration.dao.RegistrationDAO;
import com.vehicleregistration.model.Customer;
import com.vehicleregistration.model.Vehicle;
import com.vehicleregistration.model.Registration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VehicleRegistrationService {
    private final CustomerDAO customerDAO;
    private final VehicleDAO vehicleDAO;
    private final RegistrationDAO registrationDAO;
    
    public VehicleRegistrationService() {
        this.customerDAO = new CustomerDAO();
        this.vehicleDAO = new VehicleDAO();
        this.registrationDAO = new RegistrationDAO();
    }
    
    // Customer operations
    public Customer saveCustomer(Customer customer) {
        // Check for duplicate email
        Optional<Customer> existingCustomer = customerDAO.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Customer with email " + customer.getEmail() + " already exists");
        }
        customerDAO.save(customer);
        return customer;
    }
    
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerDAO.findByEmail(email);
    }
    
    public List<Customer> findCustomersByName(String firstName, String lastName) {
        return customerDAO.findByName(firstName, lastName);
    }
    
    // Vehicle operations
    public Vehicle saveVehicle(Vehicle vehicle) {
        // Check for duplicate VIN
        Optional<Vehicle> existingVehicle = vehicleDAO.findByVin(vehicle.getVin());
        if (existingVehicle.isPresent()) {
            throw new RuntimeException("Vehicle with VIN " + vehicle.getVin() + " already exists");
        }
        vehicleDAO.save(vehicle);
        return vehicle;
    }
    
    public Optional<Vehicle> findVehicleByVin(String vin) {
        return vehicleDAO.findByVin(vin);
    }
    
    // Registration operations
    public Registration registerVehicle(Customer customer, Vehicle vehicle, String licensePlate, Double registrationFee) {
        // Check if vehicle is already registered with an active registration
        List<Registration> existingRegistrations = registrationDAO.findByVehicle(vehicle);
        boolean hasActiveRegistration = existingRegistrations.stream()
            .anyMatch(reg -> "ACTIVE".equals(reg.getStatus()));
        
        if (hasActiveRegistration) {
            throw new RuntimeException("Vehicle is already registered with an active registration");
        }
        
        // Check if license plate is already taken
        Optional<Registration> existingPlate = registrationDAO.findByLicensePlate(licensePlate);
        if (existingPlate.isPresent()) {
            throw new RuntimeException("License plate " + licensePlate + " is already in use");
        }
        
        // Generate unique registration number
        String registrationNumber = generateRegistrationNumber();
        
        // Create registration
        Registration registration = new Registration(registrationNumber, licensePlate, customer, vehicle);
        registration.setRegistrationFee(registrationFee);
        
        registrationDAO.save(registration);
        return registration;
    }
    
    public Optional<Registration> findRegistrationByNumber(String registrationNumber) {
        return registrationDAO.findByRegistrationNumber(registrationNumber);
    }
    
    public Optional<Registration> findRegistrationByLicensePlate(String licensePlate) {
        return registrationDAO.findByLicensePlate(licensePlate);
    }
    
    public List<Registration> findRegistrationsByCustomerEmail(String email) {
        Optional<Customer> customer = customerDAO.findByEmail(email);
        if (customer.isPresent()) {
            return registrationDAO.findByCustomer(customer.get());
        }
        return List.of();
    }
    
    public List<Registration> getAllRegistrations() {
        return registrationDAO.findAll();
    }
    
    private String generateRegistrationNumber() {
        String prefix = "REG";
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefix + "-" + uuid;
    }
}
