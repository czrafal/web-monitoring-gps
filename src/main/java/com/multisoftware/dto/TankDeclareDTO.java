package com.multisoftware.dto;
import java.util.Date;

import com.multisoftware.model.Vehicle;

public class TankDeclareDTO {

    private Long idtanksdeclare;
    private Long idtank;
    private Long IDSystem;
    private Long IDVehicle;
    private Double lat;
    private Double litresdeclare;
    private Date adddate;
    private Date notificationdate;
    private double priceall;
    private Vehicle vehicle;

    public TankDeclareDTO() {
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

    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Long getIdtanksdeclare() {
        return idtanksdeclare;
    }

    public void setIdtanksdeclare(Long idtanksdeclare) {
        this.idtanksdeclare = idtanksdeclare;
    }

    public Long getIdtank() {
        return idtank;
    }

    public void setIdtank(Long idtank) {
        this.idtank = idtank;
    }

    public Double getLitresdeclare() {
        return litresdeclare;
    }

    public void setLitresdeclare(Double litresdeclare) {
        this.litresdeclare = litresdeclare;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public Date getNotificationdate() {
        return notificationdate;
    }

    public void setNotificationdate(Date notificationdate) {
        this.notificationdate = notificationdate;
    }

    public double getPriceall() {
        return priceall;
    }

    public void setPriceall(double priceall) {
        this.priceall = priceall;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}