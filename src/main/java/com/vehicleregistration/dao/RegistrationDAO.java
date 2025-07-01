package com.vehicleregistration.dao;

import com.vehicleregistration.model.Registration;
import com.vehicleregistration.model.Customer;
import com.vehicleregistration.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class RegistrationDAO {
    
    public void save(Registration registration) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(registration);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving registration: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
    
    public Optional<Registration> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Registration registration = session.get(Registration.class, id);
            return Optional.ofNullable(registration);
        } finally {
            session.close();
        }
    }
    
    public Optional<Registration> findByRegistrationNumber(String registrationNumber) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Registration> query = session.createQuery(
                "SELECT r FROM Registration r " +
                "JOIN FETCH r.customer " +
                "JOIN FETCH r.vehicle " +
                "WHERE r.registrationNumber = :registrationNumber", 
                Registration.class
            );
            query.setParameter("registrationNumber", registrationNumber);
            return query.uniqueResultOptional();
        } finally {
            session.close();
        }
    }
    
    public Optional<Registration> findByLicensePlate(String licensePlate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Registration> query = session.createQuery(
                "SELECT r FROM Registration r " +
                "JOIN FETCH r.customer " +
                "JOIN FETCH r.vehicle " +
                "WHERE r.licensePlate = :licensePlate", 
                Registration.class
            );
            query.setParameter("licensePlate", licensePlate);
            return query.uniqueResultOptional();
        } finally {
            session.close();
        }
    }
    
    public List<Registration> findByCustomer(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Registration> query = session.createQuery(
                "SELECT r FROM Registration r " +
                "JOIN FETCH r.customer " +
                "JOIN FETCH r.vehicle " +
                "WHERE r.customer = :customer", 
                Registration.class
            );
            query.setParameter("customer", customer);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public List<Registration> findByVehicle(Vehicle vehicle) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Registration> query = session.createQuery(
                "SELECT r FROM Registration r " +
                "JOIN FETCH r.customer " +
                "JOIN FETCH r.vehicle " +
                "WHERE r.vehicle = :vehicle", 
                Registration.class
            );
            query.setParameter("vehicle", vehicle);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public List<Registration> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Registration> query = session.createQuery(
                "SELECT r FROM Registration r " +
                "JOIN FETCH r.customer " +
                "JOIN FETCH r.vehicle", 
                Registration.class
            );
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public void update(Registration registration) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.merge(registration);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating registration: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
    
    public void delete(Registration registration) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(registration);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting registration: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}
