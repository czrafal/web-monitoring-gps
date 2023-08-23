package com.multisoftware.dto;

import com.multisoftware.model.Vehicle;

public class DriverAndVehicle {

    private Long IDDriver;
    private Long IDSystem;
    private Long IDVehicle;
    private String FName;
    private String LName;
    private String model;
    private String brand;
    private String regNum;
    private String phone;

    public DriverAndVehicle() {

    }

    public DriverAndVehicle(Long iDDriver, Long iDSystem, Long iDVehicle, String fName, String lName, String phone, String model, String brand, String reg) {
        this.IDDriver = iDDriver;
        this.IDSystem = iDSystem;
        this.IDVehicle = iDVehicle;
        this.FName = fName;
        this.LName = lName;
        this.model = model;
        this.brand = brand;
        this.phone = phone;
        this.regNum = reg;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getIDDriver() {
        return IDDriver;
    }

    public void setIDDriver(Long iDDriver) {
        IDDriver = iDDriver;
    }

    public Long getIDSystem() {
        return IDSystem;
    }

    public void setIDSystem(Long iDSystem) {
        IDSystem = iDSystem;
    }

    public Long getIDVehicle() {
        return IDVehicle;
    }

    public void setIDVehicle(Long iDVehicle) {
        IDVehicle = iDVehicle;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String fName) {
        FName = fName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String lName) {
        LName = lName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null )return false;
        if(obj instanceof String){
            if(obj.toString().equals(this.toString())){
                return true;
            }
        }
        if(obj instanceof Vehicle){
            DriverAndVehicle objGroup = (DriverAndVehicle) obj;
            if(objGroup.getIDVehicle() == this.getIDVehicle()){
                return true;
            }
        }
        return false;
    }
}
