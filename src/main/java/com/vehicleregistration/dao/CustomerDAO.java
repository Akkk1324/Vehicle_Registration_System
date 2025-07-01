package com.vehicleregistration.dao;

import com.vehicleregistration.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CustomerDAO {
    
    public void save(Customer customer) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving customer: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
    
    public Optional<Customer> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Customer customer = session.get(Customer.class, id);
            return Optional.ofNullable(customer);
        } finally {
            session.close();
        }
    }
    
    public Optional<Customer> findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Customer> query = session.createQuery("FROM Customer WHERE email = :email", Customer.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } finally {
            session.close();
        }
    }
    
    public List<Customer> findByName(String firstName, String lastName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Customer> query = session.createQuery(
                "FROM Customer WHERE firstName = :firstName AND lastName = :lastName", 
                Customer.class
            );
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public List<Customer> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Customer", Customer.class).list();
        } finally {
            session.close();
        }
    }
    
    public void update(Customer customer) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.merge(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating customer: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
    
    public void delete(Customer customer) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting customer: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}
