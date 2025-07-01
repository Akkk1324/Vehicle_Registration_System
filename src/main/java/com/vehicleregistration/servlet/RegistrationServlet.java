package com.vehicleregistration.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vehicleregistration.model.Customer;
import com.vehicleregistration.model.Vehicle;
import com.vehicleregistration.model.Registration;
import com.vehicleregistration.service.VehicleRegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/registrations/*")
public class RegistrationServlet extends HttpServlet {
    private VehicleRegistrationService service;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        service = new VehicleRegistrationService();
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        String pathInfo = req.getPathInfo();
        PrintWriter out = resp.getWriter();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all registrations
                List<Registration> registrations = service.getAllRegistrations();
                System.out.println("Found " + registrations.size() + " registrations");
                out.print(gson.toJson(registrations));
            } else if (pathInfo.startsWith("/search")) {
                String query = req.getParameter("q");
                String type = req.getParameter("type");
                
                if ("registration".equals(type)) {
                    Optional<Registration> registration = service.findRegistrationByNumber(query);
                    out.print(gson.toJson(registration.orElse(null)));
                } else if ("plate".equals(type)) {
                    Optional<Registration> registration = service.findRegistrationByLicensePlate(query);
                    out.print(gson.toJson(registration.orElse(null)));
                } else if ("email".equals(type)) {
                    List<Registration> registrations = service.findRegistrationsByCustomerEmail(query);
                    out.print(gson.toJson(registrations));
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Invalid search type\"}");
                }
            }
        } catch (Exception e) {
            System.err.println("Error in registration servlet: " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        PrintWriter out = resp.getWriter();
        
        try {
            // Debug: Print all parameters
            System.out.println("=== Request Parameters ===");
            req.getParameterMap().forEach((key, values) -> 
                System.out.println(key + " = " + String.join(", ", values)));
            
            // Extract customer data
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            String city = req.getParameter("city");
            String state = req.getParameter("state");
            String zipCode = req.getParameter("zipCode");
            
            // Extract vehicle data
            String vin = req.getParameter("vin");
            String make = req.getParameter("make");
            String model = req.getParameter("model");
            String yearStr = req.getParameter("year");
            String color = req.getParameter("color");
            String vehicleType = req.getParameter("vehicleType");
            String fuelType = req.getParameter("fuelType");
            String engineSize = req.getParameter("engineSize");
            String transmission = req.getParameter("transmission");
            
            // Extract registration data
            String licensePlate = req.getParameter("licensePlate");
            String registrationFeeStr = req.getParameter("registrationFee");
            
            // Validate required fields
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new RuntimeException("First name is required");
            }
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new RuntimeException("Last name is required");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            if (vin == null || vin.trim().isEmpty()) {
                throw new RuntimeException("VIN is required");
            }
            if (licensePlate == null || licensePlate.trim().isEmpty()) {
                throw new RuntimeException("License plate is required");
            }
            
            // Parse year and registration fee
            Integer year = null;
            Double registrationFee = null;
            
            try {
                if (yearStr != null && !yearStr.trim().isEmpty()) {
                    year = Integer.parseInt(yearStr);
                }
                if (registrationFeeStr != null && !registrationFeeStr.trim().isEmpty()) {
                    registrationFee = Double.parseDouble(registrationFeeStr);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid number format in year or registration fee");
            }
            
            // Create or find customer
            System.out.println("Creating customer with email: " + email);
            Customer customer = null;
            Optional<Customer> existingCustomer = service.findCustomerByEmail(email);
            
            if (existingCustomer.isPresent()) {
                customer = existingCustomer.get();
            } else {
                customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setAddress(address);
                customer.setCity(city);
                customer.setState(state);
                customer.setZipCode(zipCode);
                
                service.saveCustomer(customer);
            }
            
            // Create or find vehicle
            Vehicle vehicle = null;
            Optional<Vehicle> existingVehicle = service.findVehicleByVin(vin);
            
            if (existingVehicle.isPresent()) {
                vehicle = existingVehicle.get();
            } else {
                vehicle = new Vehicle();
                vehicle.setVin(vin);
                vehicle.setMake(make);
                vehicle.setModel(model);
                vehicle.setYear(year);
                vehicle.setColor(color);
                vehicle.setVehicleType(vehicleType);
                vehicle.setFuelType(fuelType);
                vehicle.setEngineSize(engineSize);
                vehicle.setTransmission(transmission);
                
                service.saveVehicle(vehicle);
            }
            
            // Create registration
            Registration registration = service.registerVehicle(customer, vehicle, licensePlate, registrationFee);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(registration));
            
        } catch (Exception e) {
            System.err.println("Error in registration servlet: " + e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
