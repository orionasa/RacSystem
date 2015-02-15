/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

import com.racsystem.car.CarData;
import com.racsystem.car.CarDataControl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author asa
 */
@ManagedBean(name = "incomeBean")
public class IncomeBean {

    private String licensePlate;
    private String rentTimes;
    private String income;
    private String total;
    private List<IncomeData> incomeList;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getRentTimes() {
        return rentTimes;
    }

    public void setRentTimes(String rentTimes) {
        this.rentTimes = rentTimes;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getTotal() {
        if (total == null) {
            List cars = new CarDataControl().showCarList();
            List rents = new RentDataControl().rentReport();
            int price = 0;
            int sum = 0;
            for (Iterator iter1 = cars.iterator(); iter1.hasNext();) {
                CarData cd = (CarData) iter1.next();
                for (Iterator iter2 = rents.iterator(); iter2.hasNext();) {
                    RentData rd = (RentData) iter2.next();
                    if (cd.getLicensePlate().equals(rd.getCarData().getLicensePlate())) {

                        price += Integer.parseInt(rd.getPrice());

                    }
                }
                sum += price;
                price = 0;
            }
            this.total = String.valueOf(sum);
        }

        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<IncomeData> getIncomeList() {
        if (incomeList == null) {
            this.incomeList = createIncomeList();
        }
        return incomeList;
    }

    public void setIncomeList(List<IncomeData> incomeList) {
        this.incomeList = incomeList;
    }

    public List<IncomeData> createIncomeList() {
        List cars = new CarDataControl().showCarList();
        List rents = new RentDataControl().rentReport();
        List<IncomeData> mylist = new ArrayList<>();
        int counter = 0;
        int price = 0;

        for (Iterator iter1 = cars.iterator(); iter1.hasNext();) {
            CarData cd = (CarData) iter1.next();
            for (Iterator iter2 = rents.iterator(); iter2.hasNext();) {
                RentData rd = (RentData) iter2.next();
                if (cd.getLicensePlate().equals(rd.getCarData().getLicensePlate())) {
                    counter++;
                    price += Integer.parseInt(rd.getPrice());

                }
            }

            mylist.add(new IncomeData(cd.getLicensePlate(), String.valueOf(counter), String.valueOf(price)));
            counter = 0;
            price = 0;
        }

        return mylist;
    }

}
