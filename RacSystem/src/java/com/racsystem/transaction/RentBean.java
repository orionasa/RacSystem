/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

import com.racsystem.car.CarData;
import java.text.ParseException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author asa
 */
@ManagedBean(name = "rentBean")
public class RentBean {

    private String carData;
    private String customerData;
    private String startDate;
    private String endDate;
    private String[] serviceData;
    private boolean selectedAddress1;
    private boolean selectedAddress2;
    private List<RentData> inRentList;
    private String addressA;
    private String officeA;
    private String addressB;
    private String officeB;
    private String price;
    private String editedCar;
    private List<CarData> rents;
    private final DateParser dp = new DateParser();
    private final RentDataControl rentDataControl = new RentDataControl();

    public List<RentData> getInRentList() throws ParseException {
        if (inRentList == null) {
            this.inRentList = rentDataControl.createRentList();
        }
        return inRentList;
    }

    public void setInRentList(List<RentData> inRentList) {
        this.inRentList = inRentList;
    }

    public boolean isSelectedAddress1() {
        return selectedAddress1;
    }

    public void setSelectedAddress1(boolean selectedAddress1) {
        this.selectedAddress1 = selectedAddress1;
    }

    public boolean isSelectedAddress2() {
        return selectedAddress2;
    }

    public void setSelectedAddress2(boolean selectedAddress2) {
        this.selectedAddress2 = selectedAddress2;
    }

    public String getOfficeA() {
        return officeA;
    }

    public void setOfficeA(String officeA) {
        this.officeA = officeA;
    }

    public String getOfficeB() {
        return officeB;
    }

    public void setOfficeB(String officeB) {
        this.officeB = officeB;
    }

    public List<CarData> getRents() {
        if (rents == null) {
            this.rents = rentDataControl.showRents();
        }
        return rents;
    }

    public void setRents(List<CarData> rents) {
        this.rents = rents;
    }
    private List<RentData> rentReport;

    public List<RentData> getRentReport() {
        if (rentReport == null) {
            this.rentReport = rentDataControl.rentReport();
        }
        return rentReport;
    }

    public void setRentReport(List<RentData> rentReport) {
        this.rentReport = rentReport;
    }

    public String getEditedCar() {
        return editedCar;
    }

    public void setEditedCar(String editedCar) {
        this.editedCar = editedCar;
    }

    public String getCarData() {
        return carData;
    }

    public void setCarData(String carData) {
        this.carData = carData;
    }

    public String getCustomerData() {
        return customerData;
    }

    public void setCustomerData(String customerData) {
        this.customerData = customerData;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String[] getServiceData() {
        return serviceData;
    }

    public void setServiceData(String[] serviceData) {
        this.serviceData = serviceData;
    }

    public String getAddressA() {
        return addressA;
    }

    public void setAddressA(String addressA) {
        this.addressA = addressA;
    }

    public String getAddressB() {
        return addressB;
    }

    public void setAddressB(String addressB) {
        this.addressB = addressB;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void calculate() throws ParseException {
        if (dp.parseDate(startDate, endDate) != -1) {
            this.price = rentDataControl.calculatePrice(carData, startDate, endDate, serviceData);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "End Date Must Be Greater Than Start ", ""));
        }
    }

    public void add() throws ParseException {
        if (selectedAddress1 && selectedAddress2) {

            if (!officeA.isEmpty() && !officeB.isEmpty()) {
                rentDataControl.createARent(carData, customerData, startDate, endDate, serviceData, officeA, officeB);
            } else {
                addressError();
            }
        } else if (selectedAddress1 && !selectedAddress2) {

            if (!addressB.isEmpty() && !officeA.isEmpty()) {
                rentDataControl.createARent(carData, customerData, startDate, endDate, serviceData, officeA, addressB);
            } else {
                addressError();
            }
        } else if (!selectedAddress1 && selectedAddress2) {

            if (!addressA.isEmpty() && !officeB.isEmpty()) {
                rentDataControl.createARent(carData, customerData, startDate, endDate, serviceData, addressA, officeB);
            } else {
                addressError();
            }
        } else {

            if (!addressA.isEmpty() && !addressB.isEmpty()) {

                rentDataControl.createARent(carData, customerData, startDate, endDate, serviceData, addressA, addressB);
            } else {
                addressError();
            }
        }

    }

    public void delete() throws ParseException {
        rentDataControl.deleteRent(editedCar);
    }

    public void addressError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Enter Delivery/Receive Address!", ""));
    }

    public void receive() {
        rentDataControl.receiveACar(editedCar);
    }

}
