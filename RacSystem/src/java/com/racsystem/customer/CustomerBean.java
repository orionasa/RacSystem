/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.customer;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author asa
 */
@ManagedBean(name = "customerBean")
public class CustomerBean {

    private String rentalType;
    private String identityNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mail;
    private String city;
    private String address;
    private String editedCustomer;
    private List<CustomerData> customerDataList;
    private final CustomerDataControl customerDataControl = new CustomerDataControl();

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEditedCustomer() {
        return editedCustomer;
    }

    public void setEditedCustomer(String editedCustomer) {
        this.editedCustomer = editedCustomer;
    }

    public List<CustomerData> getCustomerDataList() {
        if (customerDataList == null) {
            customerDataList = customerDataControl.showCustomerList();
        }
        return customerDataList;
    }

    public List<CustomerData> getCustomerList() {
        return customerDataControl.showCustomerList();
    }

    public void addCustomer() {
        if (!customerDataControl.identityNoControl(identityNo)) {
            customerDataControl.addCustomer(rentalType, identityNo, firstName, lastName, phoneNumber, mail, city, address);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Identity Number is Already In Database!", ""));
        }
    }

    public void deleteCustomer() {
        customerDataControl.deleteCustomer(this.editedCustomer);
    }

    public void updateCustomer() {
        customerDataControl.updateCustomer(rentalType, identityNo, firstName, lastName, phoneNumber, mail, city, address);
    }

    public void showEditedCustomer() {
        CustomerData customerData = customerDataControl.getCustomerData(this.editedCustomer);
        this.rentalType = customerData.getRentalType();
        this.identityNo = customerData.getIdentityNo();
        this.firstName = customerData.getFirstName();
        this.lastName = customerData.getLastName();
        this.phoneNumber = customerData.getPhoneNumber();
        this.mail = customerData.getMail();
        this.city = customerData.getCity();
        this.address = customerData.getAddress();
    }
}
