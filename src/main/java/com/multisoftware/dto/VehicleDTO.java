package com.multisoftware.dto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;

public class VehicleDTO implements Serializable {

    private Long IDVehicle;
    private String brand;
    private Date buyDate;
    private Date checkEnd;
    private Date checkStart;
    private Double combustion;
    private Long IDSystem;
    private String model;
    private Date OCDateEnd;
    private Date OCDateStart;
    private String regNum;
    private GpsDeviceDTO gpsDeviceDTO;
    private String imei;

    public VehicleDTO() {}

    public Long getIDVehicle() {
        return this.IDVehicle;
    }

    public void setIDVehicle(Long IDVehicle) {
        this.IDVehicle = IDVehicle;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getBuyDate() {
        return this.buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getCheckEnd() {
        return this.checkEnd;
    }

    public void setCheckEnd(Date checkEnd) {
        this.checkEnd = checkEnd;
    }

    public Date getCheckStart() {
        return this.checkStart;
    }

    public void setCheckStart(Date checkStart) {
        this.checkStart = checkStart;
    }

    public Double getCombustion() {
        return this.combustion;
    }

    public void setCombustion(Double combustion) {
        this.combustion = combustion;
    }

    public Long getIDSystem() {
        return this.IDSystem;
    }

    public void setIDSystem(Long IDSystem) {
        this.IDSystem = IDSystem;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getOCDateEnd() {
        return this.OCDateEnd;
    }

    public void setOCDateEnd(Date OCDateEnd) {
        this.OCDateEnd = OCDateEnd;
    }

    public Date getOCDateStart() {
        return this.OCDateStart;
    }

    public void setOCDateStart(Date OCDateStart) {
        this.OCDateStart = OCDateStart;
    }

    public String getRegNum() {
        return this.regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public GpsDeviceDTO getGpsDeviceDTO() {
        return gpsDeviceDTO;
    }

    public void setGpsDeviceDTO(GpsDeviceDTO gpsDeviceDTO) {
        this.gpsDeviceDTO = gpsDeviceDTO;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VehicleDTO that = (VehicleDTO) o;

        return new EqualsBuilder()
                .append(IDVehicle, that.IDVehicle)
                .append(brand, that.brand)
                .append(buyDate, that.buyDate)
                .append(checkEnd, that.checkEnd)
                .append(checkStart, that.checkStart)
                .append(combustion, that.combustion)
                .append(IDSystem, that.IDSystem)
                .append(model, that.model)
                .append(OCDateEnd, that.OCDateEnd)
                .append(OCDateStart, that.OCDateStart)
                .append(regNum, that.regNum)
                .append(gpsDeviceDTO, that.gpsDeviceDTO)
                .append(imei, that.imei)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(IDVehicle)
                .append(brand)
                .append(buyDate)
                .append(checkEnd)
                .append(checkStart)
                .append(combustion)
                .append(IDSystem)
                .append(model)
                .append(OCDateEnd)
                .append(OCDateStart)
                .append(regNum)
                .append(gpsDeviceDTO)
                .append(imei)
                .toHashCode();
    }
}
