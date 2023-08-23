package com.multisoftware.dto;

import com.multisoftware.model.Geopoint;
import com.multisoftware.model.Route;
import com.multisoftware.model.Vehicle;
import org.traccar.model.Device;
import org.traccar.model.Position;

import java.math.BigInteger;

public class DriverInfo {

    private BigInteger IDDriver;
    private Long IDSystem = 2l;
    private Route route;
    private double gas;
    private Integer maxspeed;
    private Geopoint geopoint;
    private boolean activeSession;
    private Device device;
    private Position position;
    private Vehicle vehicle;
    private boolean watchRoute;

    public DriverInfo(){

    }

    public DriverInfo(BigInteger IDDriver, String FName, String LName, double gas, Integer maxspeed) {
        this.IDDriver = IDDriver;
        this.gas = gas;
        this.maxspeed = maxspeed;
    }

    public BigInteger getIDDriver() {
        return IDDriver;
    }

    public void setIDDriver(BigInteger iDDriver) {
        IDDriver = iDDriver;
    }

    public Long getIDSystem() {
        return IDSystem;
    }

    public void setIDSystem(Long iDSystem) {
        IDSystem = iDSystem;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

    public Integer getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(Integer maxspeed) {
        this.maxspeed = maxspeed;
    }

    public boolean isActiveSession() {
        return activeSession;
    }

    public void setActiveSession(boolean activeSession) {
        this.activeSession = activeSession;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Geopoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(Geopoint geopoint) {
        this.geopoint = geopoint;
    }

    public boolean isWatchRoute() {
        return watchRoute;
    }

    public void setWatchRoute(boolean watchRoute) {
        this.watchRoute = watchRoute;
    }

}
