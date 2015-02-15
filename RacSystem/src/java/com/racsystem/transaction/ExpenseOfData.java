/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

/**
 *
 * @author asa
 */
public class ExpenseOfData {

    private String licensePlate;
    private String maintenanceTimes;
    private String expenseOf;

    public ExpenseOfData() {
    }

    public ExpenseOfData(String licensePlate, String maintenanceTimes, String expenseOf) {
        this.licensePlate = licensePlate;
        this.maintenanceTimes = maintenanceTimes;
        this.expenseOf = expenseOf;
    }

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
}
