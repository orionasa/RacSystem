/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

import com.racsystem.car.CarData;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author asa
 */
@ManagedBean(name = "maintenanceBean")
public class MaintenanceBean {

    private String carData;
    private String startDate;
    private String endDate;
    private String detail;
    private String price;
    private Date today;
    private List<CarData> carList;
    private List<MaintenanceData> maintenanceList;
    private final MaintenanceDataControl maintenanceDataControl = new MaintenanceDataControl();

    public String getCarData() {
        return carData;
    }

    public void setCarData(String carData) {
        this.carData = carData;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getToday() {
        return new Date();
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public List<CarData> getCarList() {
        return maintenanceDataControl.showMaintenace();
    }

    public void setCarList(List<CarData> carList) {
        this.carList = carList;
    }

    public List<MaintenanceData> getMaintenanceList() {
        if (maintenanceList == null) {
            this.maintenanceList = maintenanceDataControl.showMaintenaceList();
        }
        return maintenanceList;
    }

    public void setMaintenanceList(List<MaintenanceData> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    public void add() throws ParseException {
        maintenanceDataControl.createMaintenance(carData, startDate, endDate, detail, price);
    }

    public void delete() {
        maintenanceDataControl.deleteMaintenance(carData);
    }

    public List<CarData> showMaintenance() {
        return maintenanceDataControl.showMaintenace();
    }

    public void receive() {
        maintenanceDataControl.receiveACar(carData);
    }
}
