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
public class IncomeData {

    private String licensePlate;
    private String rentTimes;
    private String income;

    public IncomeData() {
    }

    public IncomeData(String licensePlate, String rentTimes, String income) {
        this.licensePlate = licensePlate;
        this.rentTimes = rentTimes;
        this.income = income;
    }

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

}
