package com.vehicleregistration.dao;

import com.vehicleregistration.model.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public void save(Registration registration) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(registration);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Registration findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Registration.class, id);
        }
    }

    @Override
    public Registration findByRegistrationNumber(String registrationNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Registration> query = session.createQuery("FROM Registration WHERE registrationNumber = :regNumber", Registration.class);
            query.setParameter("regNumber", registrationNumber);
            return query.uniqueResult();
        }
    }

    @Override
    public Registration findByVehicleVin(String vin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Registration> query = session.createQuery("FROM Registration WHERE vehicle.vin = :vin", Registration.class);
            query.setParameter("vin", vin);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Registration> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Registration", Registration.class).list();
        }
    }

    @Override
    public void update(Registration registration) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(registration);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Registration registration = session.get(Registration.class, id);
            if (registration != null) {
                session.remove(registration);
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