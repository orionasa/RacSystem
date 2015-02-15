/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.car;

import com.racsystem.definition.CashDataControl;
import com.racsystem.transaction.DateParser;
import com.racsystem.transaction.MaintenanceData;
import com.racsystem.transaction.MaintenanceDataControl;
import com.racsystem.transaction.RentData;
import com.racsystem.transaction.RentDataControl;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Soner
 */
@ManagedBean(name = "carBean")
public class CarBean {

    private String licensePlate;
    private String manufacturer;
    private String model;
    private String carYear;
    private String color;
    private String fuelType;
    private String transmission;
    private String status;
    private String editedCar;
    private String price;
    private List<CarData> carDataList;
    private List<CarData> inRentList;
    private final DateParser dateParser = new DateParser();
    private final CarDataControl carDataControl = new CarDataControl();

    public CarBean() throws ParseException {
        List cars = carDataControl.createSession().createQuery("FROM CarData").list();
        List rents = new RentDataControl().rentReport();
        List mains = new MaintenanceDataControl().showMaintenaceList();
        for (Iterator iter1 = cars.iterator(); iter1.hasNext();) {
            CarData car = (CarData) iter1.next();
            for (Iterator iter2 = rents.iterator(); iter2.hasNext();) {
                RentData rd = (RentData) iter2.next();
                if (car.getLicensePlate().equals(rd.getCarData().getLicensePlate())) {
                    if (dateParser.compareDate(rd.getStartDate(), rd.getEndDate())) {
                        carDataControl.changeStatus(car.getLicensePlate(), "In-Rent");
                    } else if (dateParser.receiveCar(rd.getEndDate())) {
                        carDataControl.changeStatus(car.getLicensePlate(), "Free");
                    } else if (dateParser.nonReceiveCar(rd.getStartDate())) {
                        carDataControl.changeStatus(car.getLicensePlate(), "Reserved");
                    }
                }
            }

            for (Iterator iter3 = mains.iterator(); iter3.hasNext();) {
                MaintenanceData md = (MaintenanceData) iter3.next();
                if (car.getLicensePlate().equals(md.getCarData().getLicensePlate())) {
                    if (dateParser.compareDate(md.getStartDate(), md.getEndDate())) {
                        carDataControl.changeStatus(car.getLicensePlate(), "In-Maintenance");
                    } else if (dateParser.receiveCar(md.getEndDate())) {
                        carDataControl.changeStatus(car.getLicensePlate(), "Free");
                    } else if (dateParser.nonReceiveCar(md.getStartDate())) {
                        carDataControl.changeStatus(car.getLicensePlate(), "Disabled");
                    }
                }
            }

        }
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEditedCar() {
        return editedCar;
    }

    public void setEditedCar(String editedCar) {
        this.editedCar = editedCar;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<CarData> getCarDataList() {
        if (carDataList == null) {
            carDataList = carDataControl.showCarList();
        }
        return carDataList;
    }

    public void setCarDataList(List<CarData> carDataList) {
        this.carDataList = carDataList;
    }

    public List<CarData> getInRentList() {
        if (inRentList == null) {
            this.inRentList = carDataControl.showRentList();
        }
        return inRentList;
    }

    public void setInRentList(List<CarData> inRentList) {
        this.inRentList = inRentList;
    }

    public List<CarData> getCarList() {
        return carDataControl.showCarList();
    }

    public void addCar() {
        if (!carDataControl.licensePlateControl(licensePlate)) {
            carDataControl.addCar(licensePlate, manufacturer, model, carYear, color, fuelType, transmission, status);
            new CashDataControl().definePrice(licensePlate, price);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "License Plate is Already In Database!", ""));
        }
    }

    public void deleteCar() {
        carDataControl.deleteCar(this.editedCar);
    }

    public void updateCar() {
        carDataControl.updateCar(licensePlate, manufacturer, model, carYear, color, fuelType, transmission, status);
    }

    public void showEditedCar() {
        CarData carData = carDataControl.getCarData(this.editedCar);
        this.licensePlate = carData.getLicensePlate();
        this.manufacturer = carData.getManufacturer();
        this.model = carData.getModel();
        this.carYear = carData.getCarYear();
        this.color = carData.getColor();
        this.fuelType = carData.getFuelType();
        this.transmission = carData.getTransmission();
    }

    public List<CarData> getFreeCars() {
        return carDataControl.showFreeList();
    }

}
