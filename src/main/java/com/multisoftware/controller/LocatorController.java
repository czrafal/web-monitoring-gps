package com.multisoftware.controller;

import com.multisoftware.model.GpsDevice;
import com.multisoftware.service.LocatorService;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
public class LocatorController {

    @Autowired
    private LocatorService locatorService;

    private List<GpsDevice> locatorList = new ArrayList<>();
    private GpsDevice gpsDevice = new GpsDevice();
    private List<String> locatorStringList = new ArrayList<>();
    private GpsDevice selectedDevice = new GpsDevice();

    @PostConstruct
    public void init() {
        locatorList = locatorService.allDevicesGet();
    }

    public void addLocator() {
        locatorService.addNewDevice(gpsDevice);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces", "Dodano nowy lokalizator."));
    }

    public void deleteDevice(GpsDevice id) {
        locatorService.removeDevice(id);
        locatorList.remove(id);
    }

    public void onEdit(RowEditEvent<GpsDevice> event) {
        GpsDevice device = event.getObject();
        locatorService.updateDevice(device);
    }

    public void onEditCancel(RowEditEvent<GpsDevice> event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info", "Anulowano edycję lokalizatora."));
    }

    public GpsDevice getGpsDevice() {
        return gpsDevice;
    }

    public void setGpsDevice(GpsDevice gpsDevice) {
        this.gpsDevice = gpsDevice;
    }

    public List<GpsDevice> getLocatorList() {
        return locatorList;
    }

    public void setLocatorList(ArrayList<GpsDevice> locatorList) {
        this.locatorList = locatorList;
    }

    public List<String> getLocatorStringList() {
        return locatorStringList;
    }

    public void setLocatorStringList(List<String> locatorStringList) {
        this.locatorStringList = locatorStringList;
    }

    public GpsDevice getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(GpsDevice selectedDevice) {
        this.selectedDevice = selectedDevice;
    }
}
