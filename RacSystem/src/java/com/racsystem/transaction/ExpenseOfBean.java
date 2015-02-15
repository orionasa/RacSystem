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
@ManagedBean(name = "expenseOfBean")
public class ExpenseOfBean {

    private String licensePlate;
    private String maintenanceTimes;
    private String expenseOf;
    private String total;
    private List<ExpenseOfData> expenseOfList;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMaintenanceTimes() {
        return maintenanceTimes;
    }

    public void setMaintenanceTimes(String maintenanceTimes) {
        this.maintenanceTimes = maintenanceTimes;
    }

    public String getExpenseOf() {
        return expenseOf;
    }

    public void setExpenseOf(String expenseOf) {
        this.expenseOf = expenseOf;
    }

    public String getTotal() {
        if (total == null) {
            List cars = new CarDataControl().showCarList();
            List main = new MaintenanceDataControl().showMaintenaceList();

            int price = 0;
            int sum = 0;
            for (Iterator iter1 = cars.iterator(); iter1.hasNext();) {
                CarData cd = (CarData) iter1.next();
                for (Iterator iter2 = main.iterator(); iter2.hasNext();) {
                    MaintenanceData md = (MaintenanceData) iter2.next();
                    if (cd.getLicensePlate().equals(md.getCarData().getLicensePlate())) {
                        price += Integer.parseInt(md.getPrice());
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

    public List<ExpenseOfData> getExpenseOfList() {
        if (expenseOfList == null) {
            this.expenseOfList = createExpenseOfList();
        }
        return expenseOfList;
    }

    public void setExpenseOfList(List<ExpenseOfData> expenseOfList) {
        this.expenseOfList = expenseOfList;
    }

    public List<ExpenseOfData> createExpenseOfList() {
        List cars = new CarDataControl().showCarList();
        List main = new MaintenanceDataControl().showMaintenaceList();
        List<ExpenseOfData> mylist;
        mylist = new ArrayList<>();
        int counter = 0;
        int price = 0;

        for (Iterator iter1 = cars.iterator(); iter1.hasNext();) {
            CarData cd = (CarData) iter1.next();
            for (Iterator iter2 = main.iterator(); iter2.hasNext();) {
                MaintenanceData md = (MaintenanceData) iter2.next();
                if (cd.getLicensePlate().equals(md.getCarData().getLicensePlate())) {
                    counter++;
                    price += Integer.parseInt(md.getPrice());

                }
            }

            mylist.add(new ExpenseOfData(cd.getLicensePlate(), String.valueOf(counter), String.valueOf(price)));
            counter = 0;
            price = 0;
        }

        return mylist;
    }
}
