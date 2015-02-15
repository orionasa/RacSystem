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
@ManagedBean(name = "officeBean")
public class OfficeBean {

    private String office;
    private String deletedOffice;
    private List<OfficeData> officeList;
    private int officeID;
    OfficeDataControl odc = new OfficeDataControl();

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getDeletedOffice() {
        return deletedOffice;
    }

    public void setDeletedOffice(String deletedOffice) {
        this.deletedOffice = deletedOffice;
    }

    public List<OfficeData> getOfficeList() {
        if (officeList == null) {
            officeList = odc.officeDataList();
        }
        return officeList;
    }

    public void setOfficeList(List<OfficeData> officeList) {
        this.officeList = officeList;
    }

    public void addOffice() {
        odc.addOffice(office);
    }

    public void deleteOffice() {
        try {
            officeID = Integer.parseInt(deletedOffice);
            odc.deleteOffice(officeID);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Enter a Office ID", ""));
        }
    }
}
