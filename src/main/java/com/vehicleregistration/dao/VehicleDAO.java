package com.vehicleregistration.dao;

import com.vehicleregistration.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class VehicleDAO {
    
    public void save(Vehicle vehicle) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(vehicle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving vehicle: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
    
    public Optional<Vehicle> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Vehicle vehicle = session.get(Vehicle.class, id);
            return Optional.ofNullable(vehicle);
        } finally {
            session.close();
        }
    }
    
    public Optional<Vehicle> findByVin(String vin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Vehicle> query = session.createQuery("FROM Vehicle WHERE vin = :vin", Vehicle.class);
            query.setParameter("vin", vin);
            return query.uniqueResultOptional();
        } finally {
            session.close();
        }
    }
    
    public List<Vehicle> findByMakeAndModel(String make, String model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Vehicle> query = session.createQuery(
                "FROM Vehicle WHERE make = :make AND model = :model", 
                Vehicle.class
            );
            query.setParameter("make", make);
            query.setParameter("model", model);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public List<Vehicle> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Vehicle", Vehicle.class).list();
        } finally {
            session.close();
        }
    }
    
    public void update(Vehicle vehicle) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.merge(vehicle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating vehicle: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
    
    public void delete(Vehicle vehicle) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(vehicle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting vehicle: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}
