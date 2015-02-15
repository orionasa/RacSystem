/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.definition;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author asa
 */
@ManagedBean(name = "serviceBean")
public class ServiceBean {

    private String service;
    private String price;
    private String deletedService;
    private List<ServiceData> serviceList;
    ServiceDataControl serviceDataControl = new ServiceDataControl();

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeletedService() {
        return deletedService;
    }

    public void setDeletedService(String deletedService) {
        this.deletedService = deletedService;
    }

    public List<ServiceData> getServiceList() {
        if (serviceList == null) {
            serviceList = serviceDataControl.serviceDataList();
        }
        return serviceList;
    }

    public void setServiceList(List<ServiceData> serviceList) {
        this.serviceList = serviceList;
    }

    public void addService() {
        serviceDataControl.addService(service, price);
    }

    public void deleteService() {
        try {
            serviceDataControl.deleteService(Integer.parseInt(deletedService));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Enter a Service ID", ""));
        }
    }
}
