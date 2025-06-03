package com.vehicleregistration.dao;

import com.vehicleregistration.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public void save(Vehicle vehicle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(vehicle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Vehicle findByVin(String vin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Vehicle.class, vin);
        }
    }

    @Override
    public List<Vehicle> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Vehicle", Vehicle.class).list();
        }
    }

    @Override
    public List<Vehicle> findByOwner(Long ownerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Vehicle> query = session.createQuery("FROM Vehicle WHERE owner.id = :ownerId", Vehicle.class);
            query.setParameter("ownerId", ownerId);
            return query.list();
        }
    }

    @Override
    public void update(Vehicle vehicle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(vehicle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String vin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, vin);
            if (vehicle != null) {
                session.remove(vehicle);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}