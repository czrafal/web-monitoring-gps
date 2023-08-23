package com.multisoftware.controller;

import com.multisoftware.dto.DriverAndVehicle;
import com.multisoftware.model.Driver;
import com.multisoftware.model.Vehicle;
import com.multisoftware.service.DriverListService;
import com.multisoftware.service.VehicleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.util.List;

@Component
@ViewScoped
public class DriverAndVehicleController {

    private static final Logger logger = LogManager.getLogger(DriverAndVehicleController.class);
            
    private List<Driver> driverList;
    private List<Vehicle> vehicleList;
    private Vehicle selectedVehicle;
    private DriverAndVehicle driverAndVehicle = new DriverAndVehicle();
    private Driver selectedDriver;
    private Driver newDriver = new Driver();

    @Autowired
    private DriverListService driverListBean;

    @Autowired
    private VehicleService vehicleService;

    @PostConstruct
    public void init() {
        driverList = driverListBean.allDriverShow();
        vehicleList = vehicleService.allVehiclesGet();
    }

    public void changeListener() {

    }

    public DriverAndVehicle getDriverAndVehicle() {
        return driverAndVehicle;
    }

    public void setDriverAndVehicle(DriverAndVehicle driver) {
        this.driverAndVehicle = driver;
    }

    public void addNewDriver() {
        logger.debug("Dodawanie nowego drivera!!!!");
        logger.debug("Driver fName:" + newDriver.getFName());
        if (selectedVehicle != null) {
            newDriver.setIDVehicle(selectedVehicle.getIDVehicle());
        }
        driverListBean.addNewDriver(newDriver);
        driverList.add(newDriver);
    }

    public Driver getNewDriver() {
        return newDriver;
    }

    public void setNewDriver(Driver newDriver) {
        this.newDriver = newDriver;
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Zapisano zmianę dla ", ((Driver) event.getObject()).getLName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        Driver driver = ((Driver) event.getObject());
        logger.debug("Zapisano zmianę dla " + driver.getLName());
        driverListBean.updateDriver(driver);
    }

    public void deleteDriver(Long id) {
        logger.debug("Usuwam kierowcę o id:" + id);
        Driver kierowca = driverListBean.removeDriver(id);
        driverList.remove(kierowca);
    }

    public void onCancel(RowEditEvent event) {
        logger.debug("Anulowano zmianę dla "+ ((Driver) event.getObject()).getLName());
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle vehicle) {
        this.selectedVehicle = vehicle;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

}
