/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.customer;

import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author asa
 */
public class CustomerDataControl {

    public void addCustomer(String rentalType, String identityNo, String firstName, String lastName, String phoneNumber, String mail, String city, String address) {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CustomerData cd = new CustomerData(rentalType, identityNo, firstName, lastName, phoneNumber, mail, city, address);
            session.save(cd);
            transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void updateCustomer(String rentalType, String identityNo, String firstName, String lastName, String phoneNumber, String mail, String city, String address) {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CustomerData customerData = (CustomerData) session.get(CustomerData.class, searchCustomerID(identityNo));
            customerData.setRentalType(rentalType);
            customerData.setIdentityNo(identityNo);
            customerData.setFirstName(firstName);
            customerData.setLastName(lastName);
            customerData.setPhoneNumber(phoneNumber);
            customerData.setMail(mail);
            customerData.setCity(city);
            customerData.setAddress(address);
            session.update(customerData);
            transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();

        }
    }

    public void deleteCustomer(String identityNo) {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CustomerData customerData = (CustomerData) session.get(CustomerData.class, searchCustomerID(identityNo));
            session.delete(customerData);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public boolean identityNoControl(String identityNo) {
        List customers = createSession().createQuery("FROM CustomerData").list();
        for (Iterator iter = customers.iterator(); iter.hasNext();) {
            CustomerData cus = (CustomerData) iter.next();
            if (identityNo.equals(cus.getIdentityNo())) {
                return true;
            }
        }
        return false;
    }

    public CustomerData getCustomerData(String identityNo) {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CustomerData customerData = (CustomerData) session.get(CustomerData.class, searchCustomerID(identityNo));
            transaction.commit();
            return customerData;
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public int searchCustomerID(String identityNo) {
        List customers = createSession().createQuery("FROM CustomerData").list();

        for (Iterator iter = customers.iterator(); iter.hasNext();) {
            CustomerData customer = (CustomerData) iter.next();
            if (identityNo.equals(customer.getIdentityNo())) {
                return customer.getId();
            }
        }
        return -1;
    }

    public List<CustomerData> showCustomerList() {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List customerDataList = session.createQuery("FROM CustomerData").list();
            transaction.commit();
            return customerDataList;
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
    }

    public Session createSession() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }

}
