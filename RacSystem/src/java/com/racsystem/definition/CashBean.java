/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.definition;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author asa
 */
@ManagedBean(name = "cashBean")
@SessionScoped
public class CashBean {

    private String price;
    private String carData;
    private String editedCar;
    private boolean add_or_update;
    CashDataControl cdc = new CashDataControl();

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCarData() {
        return carData;
    }

    public void setCarData(String carData) {
        this.carData = carData;
    }

    public String getEditedCar() {
        return editedCar;
    }

    public void setEditedCar(String editedCar) {
        this.editedCar = editedCar;
    }

    public List<CashData> getList() {
        return cdc.showList();
    }

    public void add() {
        this.add_or_update = false;
        this.carData = this.editedCar;
    }

    public void edit() {
        this.add_or_update = true;
        this.carData = this.editedCar;
    }

    public void delete() {
        cdc.deletePrice(carData);
    }

    public void define() {
        if (!this.add_or_update) {
            cdc.definePrice(carData, price);
        } else {
            cdc.updatePrice(carData, price);
        }
        this.editedCar = " ";
    }
}
