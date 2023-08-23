package com.multisoftware.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.multisoftware.dto.DriverInfo;
import com.multisoftware.service.VehicleService;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class RealVehiclesController implements Serializable {

    private static final long serialVersionUID = 7160455397627208242L;

    @Autowired
    private VehicleService vehicleService;

    private DriverInfo watchSelectedCar;
    private List<DriverInfo> vehicles;
    private DriverInfo selectedVehicle;

    @PostConstruct
    public void vehicleListRealInit() {
        vehicles = vehicleService.allDriverRealShow();
    }

    public void onRowSelect(SelectEvent event) {
        DriverInfo driver = ((DriverInfo) event.getObject());
        if (driver.getGeopoint() != null && driver.getGeopoint().getLon() != 0 && driver.getGeopoint().getLat() != 0) {
            double latitude = driver.getGeopoint().getLat();
            double longitude = driver.getGeopoint().getLon();
            PrimeFaces.current().executeScript("moveMap("+ latitude +", "+ longitude+");");
        } else {
            FacesMessage msg = new FacesMessage("Brak danych lokalizacyjnych");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void vehicleListListener() {
        List<DriverInfo> activeDrivers = vehicleService.allDriverRealShow();
        if (activeDrivers != null) {
            findAndSetWatchAlreadySelectedCar(activeDrivers, watchSelectedCar);
            vehicles = activeDrivers;
        }
    }

    public void updateVehicleCacheWatchRoute(DriverInfo driverInfo) {
        for (DriverInfo driver : vehicles) {
            if (driver.getVehicle().getIDVehicle().equals(driverInfo.getVehicle().getIDVehicle())) {
                driver.setWatchRoute(driverInfo.isWatchRoute());
                setWatchSelectedCar(driverInfo);
                executeViewLastRoute(driver);
            } else {
                driver.setWatchRoute(false);
            }
        }
    }

    private void findAndSetWatchAlreadySelectedCar(List<DriverInfo> activeDrivers, DriverInfo watchedCar) {
        if (watchedCar == null) {
            return;
        }

        if (watchedCar.getGeopoint() == null && watchedCar.getGeopoint().getLon() == 0 && watchedCar.getGeopoint().getLat() == 0) {
            return;
        }

        for (DriverInfo driverInfo : activeDrivers) {
            if (driverInfo.getVehicle().getIDVehicle() == watchedCar.getVehicle().getIDVehicle()) {
                driverInfo.setWatchRoute(watchedCar.isWatchRoute());
            }
        }
    }

    private void executeViewLastRoute(DriverInfo driver) {
        if (driver.getGeopoint() != null && driver.getGeopoint().getLon() != 0 && driver.getGeopoint().getLat() != 0) {
            PrimeFaces.current().executeScript("viewLastRoute("+ driver.getGeopoint().getLat() +", "+ driver.getGeopoint().getLon()+", '"+ driver.getRoute().getIDRoutes() +"', "+ driver.isWatchRoute()+");");
        } else {
            FacesMessage msg = new FacesMessage("Brak danych lokalizacyjnych");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void testDynamicMapActionListener() {
        DriverInfo dri = new DriverInfo();
        DriverInfo driToRemove = new DriverInfo();
        for (DriverInfo driver : vehicles) {
            if (driver.getVehicle() != null && driver.getVehicle().getRegNum().equals("GBY 105J")) {
                dri = driver;
                driToRemove = driver;
                dri.getGeopoint().setLat(driver.getGeopoint().getLat() + 10);
                dri.getGeopoint().setLon(driver.getGeopoint().getLon() + 2);
            }
        }
        vehicles.remove(driToRemove);
        vehicles.add(dri);
    }

    public List<DriverInfo> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<DriverInfo> vehicles) {
        this.vehicles = vehicles;
    }

    public DriverInfo getWatchSelectedCar() {
        return watchSelectedCar;
    }

    public void setWatchSelectedCar(DriverInfo watchSelectedCar) {
        this.watchSelectedCar = watchSelectedCar;
    }

    public DriverInfo getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(DriverInfo selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }
}
