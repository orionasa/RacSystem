/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.car;

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
 * @author Soner
 */
public class CarDataControl {

    public void addCar(String licensePlate, String manufacturer, String model, String carYear, String color, String fuelType, String transmission, String status) {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CarData carData = new CarData(licensePlate, manufacturer, model, carYear, color, fuelType, transmission, status);
            session.save(carData);
            transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CAR ADDING DONE!", ""));
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void updateCar(String licensePlate, String manufacturer, String model, String carYear, String color, String fuelType, String transmission, String status) {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CarData carData = (CarData) session.get(CarData.class, searchCarID(licensePlate));
            carData.setLicensePlate(licensePlate);
            carData.setManufacturer(manufacturer);
            carData.setModel(model);
            carData.setCarYear(carYear);
            carData.setColor(color);
            carData.setFuelType(fuelType);
            carData.setTransmission(transmission);
            carData.setStatus(status);
            session.update(carData);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void deleteCar(String licensePlate) {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CarData carData = (CarData) session.get(CarData.class, searchCarID(licensePlate));
            session.delete(carData);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public boolean licensePlateControl(String licenseplate) {
        List cars = createSession().createQuery("FROM CarData").list();
        for (Iterator iter = cars.iterator(); iter.hasNext();) {
            CarData car = (CarData) iter.next();
            if (licenseplate.equals(car.getLicensePlate())) {
                return true;
            }
        }
        return false;
    }

    public CarData getCarData(String licensePlate) {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CarData carData = (CarData) session.get(CarData.class, searchCarID(licensePlate));
            transaction.commit();
            return carData;
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

    public int searchCarID(String licensePlate) {
        List cars = createSession().createQuery("FROM CarData").list();

        for (Iterator iter = cars.iterator(); iter.hasNext();) {
            CarData car = (CarData) iter.next();
            if (licensePlate.equals(car.getLicensePlate())) {
                return car.getId();
            }
        }
        return -1;
    }

    public List<CarData> showCarList() {
        Session session = createSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List carDataList = session.createQuery("FROM CarData").list();
            transaction.commit();
            return carDataList;
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

    public List<CarData> showFreeList() {
        return createSession().createQuery("FROM CarData WHERE Status='Free'").list();
    }

    public List<CarData> showRentList() {
        return createSession().createQuery("FROM CarData WHERE Status='In-Rent'").list();
    }
    
    public void changeStatus(String carData, String status) {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CarData cd = (CarData) session.get(CarData.class, searchCarID(carData));
            cd.setStatus(status);
            session.update(cd);
            tx.commit();
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Database Connection Error!", ""));
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public Session createSession() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }

}
