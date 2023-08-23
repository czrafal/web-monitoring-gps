package com.multisoftware.controller;

import com.multisoftware.dto.GpsDeviceDTO;
import com.multisoftware.dto.VehicleDTO;
import com.multisoftware.model.GpsDevice;
import com.multisoftware.model.Vehicle;
import com.multisoftware.service.EntityConverterService;
import com.multisoftware.service.LocatorService;
import com.multisoftware.service.VehicleService;
import com.multisoftware.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
public class VehiclesController implements Serializable {

    private static final Logger logger = LogManager.getLogger(VehiclesController.class);
            
    private Vehicle vehicle = new Vehicle();
    private List<Vehicle> vehicleList;
    private List<VehicleDTO> vehicleDTOList = new ArrayList<>();
    private List<GpsDevice> devicesList = new ArrayList<>();
    private List<String> devicesStringList = new ArrayList<>();
    private List<GpsDeviceDTO> devicesDtoList = new ArrayList<>();
    private Vehicle selectedVehicle;
    private String selectedImei;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private LocatorService locatorService;

    @Autowired
    private EntityConverterService entityConverterService;

    @PostConstruct
    public void init() {
        vehicleList = vehicleService.allVehiclesGet();
        devicesList = locatorService.allDevicesGet();
        gpsEntityToDTOList();
        vehicleEntityToDTOList();
        locatorStringList();
    }

    public void addVehicle() {
        GpsDevice gpsDevice = locatorService.getDeviceByImei(getSelectedImei());
        vehicle.setGpsDevice(gpsDevice);
        vehicleService.addNewVehilce(vehicle);
        vehicleList.add(vehicle);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces", "Dodano nowy pojazd."));
    }

    public List<String> locatorStringList() {
        for (GpsDevice device : devicesList) {
            devicesStringList.add(device.getImei());
        }
        return devicesStringList;
    }

    public void gpsEntityToDTOList() {
        for (GpsDevice device : devicesList) {
            devicesDtoList.add(entityConverterService.convertToDeviceDTO(device));
        }
    }

    public void vehicleEntityToDTOList() {
        for (Vehicle vehicle : vehicleList) {
            vehicleDTOList.add(entityConverterService.convertToVehicleDTO(vehicle));
        }
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public void deleteVehicle(Vehicle id) {
        //Vehicle vehicle = entityConverterService.convertToVehicleEntity(id);
        vehicleService.removeVehicle(vehicle);
        vehicleList.remove(id);
    }

    public void onRowCancel(RowEditEvent<VehicleDTO> event) throws ValidatorException {
        logger.debug("Anulowalem edycję!");
    }

    public void onRowEdit(RowEditEvent<VehicleDTO> event) throws ValidatorException {
        logger.debug("Edytowałem imei!");
        GpsDevice gpsDevice = locatorService.getDeviceByImei(getSelectedImei());
        vehicle.setGpsDevice(gpsDevice);
        VehicleDTO vehicle = event.getObject();
        /*Vehicle vehicleEnity = entityConverterService.convertToVehicleEntity(vehicle);
        vehicleService.updateVehicle(vehicleEnity);*/
    }

    public void onValueChange(ValueChangeEvent e) {
        logger.info("Value change");
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<GpsDevice> getDevicesList() {
        return devicesList;
    }

    public void setDevicesList(List<GpsDevice> devicesList) {
        this.devicesList = devicesList;
    }

    public List<String> getDevicesStringList() {
        return devicesStringList;
    }

    public void setDevicesStringList(List<String> devicesStringList) {
        this.devicesStringList = devicesStringList;
    }

    public List<VehicleDTO> getVehicleDTOList() {
        return vehicleDTOList;
    }

    public void setVehicleDTOList(List<VehicleDTO> vehicleDTOList) {
        this.vehicleDTOList = vehicleDTOList;
    }

    public List<GpsDeviceDTO> getDevicesDtoList() {
        return devicesDtoList;
    }

    public void setDevicesDtoList(List<GpsDeviceDTO> devicesDtoList) {
        this.devicesDtoList = devicesDtoList;
    }

    public String getSelectedImei() {
        return selectedImei;
    }

    public void setSelectedImei(String selectedImei) {
        this.selectedImei = selectedImei;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public void editVehicle(Vehicle vehicle) {
        setSelectedVehicle(vehicle);
    }

    public void saveVehicleEdit() {
        Vehicle vehicle = getSelectedVehicle();
        if (StringUtils.isNotEmpty(selectedImei)) {
           GpsDevice gpsDevice = locatorService.getDeviceByImei(selectedImei);
           vehicle.setGpsDevice(gpsDevice);
        }

       // Vehicle vehicleEnity = entityConverterService.convertToVehicleEntity(vehicle);
        vehicleService.updateVehicle(vehicle);
    }

    public String formatDate(java.util.Date date) {
        if (date != null) {
            return DateUtils.formatDate(date);
        }
        return null;
    }

    public String formatDate(java.sql.Date date) {
        if (date != null) {
            return DateUtils.formatSqlDate(date);
        }
        return null;
    }

    public void clearSelectedData() {
        selectedVehicle = null;
        selectedImei = null;
    }

}
