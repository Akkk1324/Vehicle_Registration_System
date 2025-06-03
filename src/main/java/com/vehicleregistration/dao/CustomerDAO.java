package com.vehicleregistration.dao;

import com.vehicleregistration.model.Customer;
import java.util.List;

public interface CustomerDAO {
    void save(Customer customer);
    Customer findById(Long id);
    Customer findByEmail(String email);
    List<Customer> findAll();
    void update(Customer customer);
    void delete(Long id);
}