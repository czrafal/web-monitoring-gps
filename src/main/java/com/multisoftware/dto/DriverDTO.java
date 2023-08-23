package com.multisoftware.dto;
public class DriverDTO {

    private String FName;
    private Long IDDriver;
    private Long IDSystem;
    private Long IDVehicle;
    private String LName;
    private String phone;
    private VehicleDTO vehicle;

    public DriverDTO() {

    }

    public Long getIDDriver() {
        return this.IDDriver;
    }

    public void setIDDriver(Long IDDriver) {
        this.IDDriver = IDDriver;
    }

    public String getFName() {
        return this.FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public Long getIDSystem() {
        return this.IDSystem;
    }

    public void setIDSystem(Long IDSystem) {
        this.IDSystem = IDSystem;
    }

    public Long getIDVehicle() {
        return this.IDVehicle;
    }

    public void setIDVehicle(Long IDVehicle) {
        this.IDVehicle = IDVehicle;
    }

    public String getLName() {
        return this.LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public VehicleDTO getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

}