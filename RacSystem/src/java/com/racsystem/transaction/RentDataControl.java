/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

import com.racsystem.car.CarData;
import com.racsystem.car.CarDataControl;
import com.racsystem.customer.CustomerData;
import com.racsystem.customer.CustomerDataControl;
import com.racsystem.definition.CashData;
import com.racsystem.definition.CashDataControl;
import com.racsystem.definition.ServiceData;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class RentDataControl {

    private final CashDataControl cdc = new CashDataControl();
    private final CarDataControl cardc = new CarDataControl();
    private final DateParser dp = new DateParser();
    List<RentData> inRentList = new ArrayList<>();

    public Session createSession() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }

    public boolean createARent(String carData, String customerData, String startDate, String endDate, String[] serviceData, String addressA, String addressB) throws ParseException {
        Session session = createSession();
        Transaction transaction = null;
        CustomerDataControl cdc2 = new CustomerDataControl();
        CarDataControl cdc1 = new CarDataControl();
        try {
            transaction = session.beginTransaction();
            CarData cd1 = (CarData) session.get(CarData.class, cdc1.searchCarID(carData));
            CustomerData cd2 = (CustomerData) session.get(CustomerData.class, cdc2.searchCustomerID(customerData));
            RentData rd = new RentData(cd1, cd2, startDate, endDate, Arrays.toString(serviceData), addressA, addressB, calculatePrice(carData, startDate, endDate, serviceData));
            session.save(rd);
            cardc.changeStatus(carData, "Reserved");
            transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
            return true;
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DATABASE ERROR", ""));

            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean updateARent(String ID, String carData, String customerData, String startDate, String endDate, String[] serviceData, String addressA, String addressB) throws ParseException {
        Session session = createSession();
        Transaction transaction = null;
        CustomerDataControl cdc2 = new CustomerDataControl();
        CarDataControl cdc1 = new CarDataControl();
        try {
            transaction = session.beginTransaction();
            CarData cd1 = (CarData) session.get(CarData.class, cdc1.searchCarID(carData));
            CustomerData cd2 = (CustomerData) session.get(CustomerData.class, cdc2.searchCustomerID(customerData));
            RentData rd = (RentData) session.get(RentData.class, Integer.parseInt(ID));

            rd.setCarData(cd1);
            rd.setCustomerData(cd2);
            rd.setStartDate(startDate);
            rd.setEndDate(endDate);
            rd.setAddressA(addressA);
            rd.setAddressB(addressB);
            rd.setServiceData(Arrays.toString(serviceData));
            rd.setPrice(calculatePrice(carData, startDate, endDate, serviceData));
            session.update(rd);
            cardc.changeStatus(carData, "Reserved");
            transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DONE!", ""));
            return true;
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DATABASE ERROR", ""));

            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public void receiveACar(String carData) {
        cardc.changeStatus(carData, "Free");
    }

    public List<RentData> createRentList() throws ParseException {
        List rents = createSession().createQuery("FROM RentData").list();
        for (Iterator iter = rents.iterator(); iter.hasNext();) {
            RentData rd = (RentData) iter.next();
            if (dp.compareDate(rd.getStartDate(), rd.getEndDate())) {
                inRentList.add(rd);
            }
        }
        return inRentList;
    }

    public List<RentData> rentReport() {
        return createSession().createQuery("FROM RentData").list();
    }

    public int calculateServicePrice(String[] serviceData) {
        int servicePrice = 0;
        List services = createSession().createQuery("FROM ServiceData").list();
        if (serviceData.length == 0) {
            return servicePrice;
        } else {
            for (String x : serviceData) {
                for (Iterator iter = services.iterator(); iter.hasNext();) {
                    ServiceData sd = (ServiceData) iter.next();
                    if (x.equals(sd.getService())) {
                        servicePrice += Integer.parseInt(sd.getPrice());
                    }
                }
            }
            return servicePrice;
        }

    }

    public int calculateCarPrice(String carData) {
        Session session = createSession();
        CashData cashData = (CashData) session.get(CashData.class, cdc.searchCashID(carData));
        return Integer.parseInt(cashData.getPrice());
    }

    public String calculatePrice(String carData, String startDate, String endDate, String[] serviceData) throws ParseException {
        return String.valueOf((dp.parseDate(startDate, endDate) * calculateCarPrice(carData) + calculateServicePrice(serviceData)));
    }

    public void deleteRent(String carData) throws ParseException {
        Session session = createSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            RentData rd = (RentData) session.get(RentData.class, Integer.parseInt(carData));
            cardc.changeStatus(rd.getCarData().getLicensePlate(), "Free");
            session.delete(rd);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Done!", ""));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

    }

    public int searchRentID(String carData) throws ParseException {
        List rents = createRentList();
        for (Iterator iter = rents.iterator(); iter.hasNext();) {
            RentData rd = (RentData) iter.next();
            if (carData.equals(rd.getCarData().getLicensePlate())) {
                return rd.getId();
            }
        }
        return -1;
    }

    public RentData getRentData(String carData) throws ParseException {
        List rents = createRentList();
        for (Iterator iter = rents.iterator(); iter.hasNext();) {
            RentData rd = (RentData) iter.next();
            if (carData.equals(String.valueOf(rd.getId()))) {
                return rd;
            }
            
        }
        return new RentData();
    }

    public List<CarData> showRents() {
        return createSession().createQuery("FROM CarData WHERE Status='Reserved'").list();
    }

}
