/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.definition;

import com.racsystem.car.CarData;
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
public class CashDataControl {

    public void definePrice(String carData, String price) {
        if (!carCashControl(carData)) {
            Session session = createSession();
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                CarData cd = (CarData) session.get(CarData.class, searchCarID(carData));
                CashData cashData = new CashData(price, cd);
                session.save(cashData);
                transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
            } catch (HibernateException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DATABASE ERROR", ""));
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                session.close();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PRICE ALLREADY DEFINED!", ""));
        }
    }

    public void updatePrice(String carData, String price) {
        Session session = createSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CarData cd = (CarData) session.get(CarData.class, searchCarID(carData));
            CashData cashData = (CashData) session.get(CashData.class, searchCashID(carData));
            cashData.setCarData(cd);
            cashData.setPrice(price);
            session.update(cashData);
            transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, ex.getMessage(), ""));
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void deletePrice(String licensePlate) {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CashData cd = (CashData) session.get(CashData.class, searchCashID(licensePlate));
            session.delete(cd);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
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

    public int searchCashID(String licensePlate) {
        List cash = createSession().createQuery("FROM CashData").list();

        for (Iterator iter = cash.iterator(); iter.hasNext();) {
            CashData ch = (CashData) iter.next();
            if (licensePlate.equals(ch.getCarData().getLicensePlate())) {
                return ch.getId();
            }
        }
        return -1;
    }

    public List<CashData> showList() {
        return createSession().createQuery("FROM CashData").list();
    }

    public boolean carCashControl(String carData) {
        List cash = createSession().createQuery("FROM CashData").list();

        for (Iterator iter = cash.iterator(); iter.hasNext();) {
            CashData ch = (CashData) iter.next();
            if (carData.equals(ch.getCarData().getLicensePlate())) {
                return true;
            }
        }
        return false;
    }

    public Session createSession() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }
}
