/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.home;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Soner
 */
@ManagedBean(name = "homeBean")
public class HomeBean {

    public String navigation = "main.jsf";

    public String navigation() {
        return navigation;
    }

    public void main() {
        navigation = "main.jsf";
    }

    public void add_car() {
        navigation = "add_car.jsf";
    }

    public void show_car_list() {
        navigation = "show_car_list.jsf";
    }

    public void add_customer() {
        navigation = "add_customer.jsf";
    }

    public void show_customer_list() {
        navigation = "show_customer_list.jsf";
    }

    public void define_price() {
        navigation = "define_price.jsf";
    }

    public void define_office() {
        navigation = "define_office.jsf";
    }

    public void define_service() {
        navigation = "define_service.jsf";
    }

    public void rent_a_car() {
        navigation = "rent_a_car.jsf";
    }

    public void add_maintenance() {
        navigation = "add_maintenance.jsf";
    }

    public void show_maintenance_list() {
        navigation = "show_maintenance_list.jsf";
    }
    
    public void show_rent_list(){
        navigation = "show_rent_list.jsf";
    }
    
    public void rent_report(){
        navigation = "rent_report.jsf";
    }
    
    public void show_income_list(){
        navigation = "show_income_list.jsf";
    }
    
    public void show_expenseof_list(){
        navigation = "show_expenseof_list.jsf";
    }
}
