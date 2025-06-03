package com.vehicleregistration.service;

import com.vehicleregistration.dao.*;
import com.vehicleregistration.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private final CustomerDAO customerDAO;
    private final VehicleDAO vehicleDAO;
    private final RegistrationDAO registrationDAO;

    public RegistrationServiceImpl() {
        this.customerDAO = new CustomerDAOImpl();
        this.vehicleDAO = new VehicleDAOImpl();
        this.registrationDAO = new RegistrationDAOImpl();
    }

    @Override
    public void registerVehicle(VehicleRegistrationDTO registrationDTO) {
        // Check if customer exists or create new
        Customer customer = customerDAO.findByEmail(registrationDTO.getEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setFirstName(registrationDTO.getFirstName());
            customer.setLastName(registrationDTO.getLastName());
            customer.setEmail(registrationDTO.getEmail());
            customer.setPhone(registrationDTO.getPhone());
            customer.setAddress(registrationDTO.getAddress());
            customerDAO.save(customer);
        }

        // Create vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(registrationDTO.getVin());
        vehicle.setMake(registrationDTO.getMake());
        vehicle.setModel(registrationDTO.getModel());
        vehicle.setYear(Year.of(registrationDTO.getYear()));
        vehicle.setColor(registrationDTO.getColor());
        vehicle.setOwner(customer);
        vehicleDAO.save(vehicle);

        // Create registration
        Registration registration = new Registration();
        registration.setRegistrationNumber(generateRegistrationNumber());
        registration.setIssueDate(LocalDate.now());
        registration.setExpiryDate(LocalDate.now().plusYears(1));
        registration.setVehicle(vehicle);
        registrationDAO.save(registration);
    }

    @Override
    public VehicleDetailsDTO getVehicleDetails(String vin) {
        Vehicle vehicle = vehicleDAO.findByVin(vin);
        if (vehicle == null) {
            return null;
        }

        Registration registration = registrationDAO.findByVehicleVin(vin);
        Customer owner = vehicle.getOwner();

        return new VehicleDetailsDTO(
                vehicle.getVin(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear().getValue(),
                vehicle.getColor(),
                owner.getFirstName() + " " + owner.getLastName(),
                owner.getEmail(),
                owner.getPhone(),
                registration.getRegistrationNumber(),
                registration.getIssueDate(),
                registration.getExpiryDate()
        );
    }

    @Override
    public CustomerVehiclesDTO getCustomerVehicles(Long customerId) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            return null;
        }

        List<Vehicle> vehicles = vehicleDAO.findByOwner(customerId);
        List<VehicleSummaryDTO> vehicleSummaries = vehicles.stream()
                .map(v -> {
                    Registration reg = registrationDAO.findByVehicleVin(v.getVin());
                    return new VehicleSummaryDTO(
                            v.getVin(),
                            v.getMake(),
                            v.getModel(),
                            v.getYear().getValue(),
                            reg.getRegistrationNumber(),
                            reg.getExpiryDate()
                    );
                })
                .collect(Collectors.toList());

        return new CustomerVehiclesDTO(
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                vehicleSummaries
        );
    }

    @Override
    public List<VehicleDetailsDTO> searchVehicles(String searchTerm) {
        List<Vehicle> vehicles = vehicleDAO.findAll().stream()
                .filter(v -> v.getVin().contains(searchTerm) || 
                           v.getMake().contains(searchTerm) || 
                           v.getModel().contains(searchTerm))
                .collect(Collectors.toList());

        return vehicles.stream()
                .map(v -> {
                    Registration reg = registrationDAO.findByVehicleVin(v.getVin());
                    Customer owner = v.getOwner();
                    return new VehicleDetailsDTO(
                            v.getVin(),
                            v.getMake(),
                            v.getModel(),
                            v.getYear().getValue(),
                            v.getColor(),
                            owner.getFirstName() + " " + owner.getLastName(),
                            owner.getEmail(),
                            owner.getPhone(),
                            reg.getRegistrationNumber(),
                            reg.getIssueDate(),
                            reg.getExpiryDate()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateRegistration(RegistrationUpdateDTO updateDTO) {
        Registration registration = registrationDAO.findByVehicleVin(updateDTO.getVin());
        if (registration != null) {
            registration.setExpiryDate(updateDTO.getNewExpiryDate());
            registrationDAO.update(registration);
        }
    }

    private String generateRegistrationNumber() {
        // Simple implementation - in production use a more robust method
        return "REG-" + System.currentTimeMillis();
    }
}