package com.vehicleregistration.controller;

import com.vehicleregistration.model.*;
import com.vehicleregistration.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // This should match your Thymeleaf template name
    }
    
    @GetMapping("/")
    public String home() {
        return "index"; // This should match your home page template
    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("registration", new VehicleRegistrationDTO());
//        return "register-vehicle";
//    }

    @PostMapping("/register")
    public String registerVehicle(@ModelAttribute VehicleRegistrationDTO registration, Model model) {
        registrationService.registerVehicle(registration);
        model.addAttribute("message", "Vehicle registered successfully!");
        return "registration-success";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search-vehicle";
    }

    @GetMapping("/details")
    public String getVehicleDetails(@RequestParam String vin, Model model) {
        VehicleDetailsDTO details = registrationService.getVehicleDetails(vin);
        model.addAttribute("vehicle", details);
        return "vehicle-details";
    }

    @GetMapping("/customer")
    public String getCustomerVehicles(@RequestParam Long customerId, Model model) {
        CustomerVehiclesDTO customerVehicles = registrationService.getCustomerVehicles(customerId);
        model.addAttribute("customer", customerVehicles);
        return "customer-vehicles";
    }

    @GetMapping("/list")
    public String listAllVehicles(Model model) {
        List<VehicleDetailsDTO> vehicles = registrationService.searchVehicles("");
        model.addAttribute("vehicles", vehicles);
        return "vehicle-list";
    }

    @GetMapping("/search-results")
    public String searchVehicles(@RequestParam String searchTerm, Model model) {
        List<VehicleDetailsDTO> results = registrationService.searchVehicles(searchTerm);
        model.addAttribute("vehicles", results);
        model.addAttribute("searchTerm", searchTerm);
        return "search-results";
    }

    @GetMapping("/renew")
    public String showRenewalForm(@RequestParam String vin, Model model) {
        VehicleDetailsDTO vehicle = registrationService.getVehicleDetails(vin);
        RegistrationUpdateDTO updateDTO = new RegistrationUpdateDTO();
        updateDTO.setVin(vin);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("update", updateDTO);
        return "renew-registration";
    }

    @PostMapping("/renew")
    public String renewRegistration(@ModelAttribute RegistrationUpdateDTO update, Model model) {
        registrationService.updateRegistration(update);
        model.addAttribute("message", "Registration renewed successfully!");
        return "renewal-success";
    }
}