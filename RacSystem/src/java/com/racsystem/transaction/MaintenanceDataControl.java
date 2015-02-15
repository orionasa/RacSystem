/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

import com.racsystem.car.CarData;
import com.racsystem.car.CarDataControl;
import java.text.ParseException;
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
public class MaintenanceDataControl {

    private final CarDataControl cdc = new CarDataControl();
    private final DateParser dp = new DateParser();

    public Session createSession() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }

    public void createMaintenance(String carData, String startDate, String endDate, String detail, String price) throws ParseException {
        Session session = createSession();
        Transaction transaction = null;
        if (dp.parseDate(startDate, endDate) == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "End Date Must Be Greater Than Start Date", ""));
        } else {
            try {
                transaction = session.beginTransaction();
                CarData cd = (CarData) session.get(CarData.class, cdc.searchCarID(carData));
                MaintenanceData md = new MaintenanceData(cd, startDate, endDate, detail, price);
                session.save(md);
                cdc.changeStatus(carData, "Disabled");
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
        }
    }

    public void deleteMaintenance(String carData) {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            MaintenanceData maintenanceData = (MaintenanceData) session.get(MaintenanceData.class, Integer.parseInt(carData));
            cdc.changeStatus(maintenanceData.getCarData().getLicensePlate(), "Free");
            session.delete(maintenanceData);
            tx.commit();
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ID NOT FOUND!", ""));
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public List<CarData> showMaintenace() {
        return createSession().createQuery("FROM CarData WHERE Status='In-Maintenance'").list();
    }

    public void receiveACar(String carData) {
        cdc.changeStatus(carData, "Free");
    }
    public List<MaintenanceData> showMaintenaceList() {
        return createSession().createQuery("FROM MaintenanceData").list();
    }
}
