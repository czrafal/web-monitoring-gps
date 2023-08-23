package com.multisoftware.dto;

import java.sql.Timestamp;
import java.util.List;

import com.multisoftware.model.Driver;
import com.multisoftware.model.Geopoint;
import com.multisoftware.model.Stop;
import com.multisoftware.model.SystemUser;
import com.multisoftware.model.Tank;
import com.multisoftware.model.Vehicle;

public class RouteFromEntityDTO {

    private Long IDRoutes;
    private Timestamp dateEnd;
    private Timestamp dateStart;
    private Double distanceSum;
    private Double fuelCostAll;
    private Double fuelCostPerKM;
    private Double fuelEnd;
    private String fuelGet;
    private Double fuelStart;
    private Long IDDriver;
    private Long IDSystem;
    private Long IDVehicle;
    private Double lat;
    private Double lon;
    private String stop;
    private String tank;
    private List<Geopoint> geopoints;
    private Driver driver;
    private SystemUser systemUser;
    private Vehicle vehicle;
    private List<Stop> stops;
    private List<Tank> tanks;
    private String timePeriodString;

    public RouteFromEntityDTO() {
    }

    public Long getIDRoutes() {
        return this.IDRoutes;
    }

    public void setIDRoutes(Long IDRoutes) {
        this.IDRoutes = IDRoutes;
    }

    public Timestamp getDateEnd() {
        return this.dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Timestamp getDateStart() {
        return this.dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Double getDistanceSum() {
        return this.distanceSum;
    }

    public void setDistanceSum(Double distanceSum) {
        this.distanceSum = distanceSum;
    }

    public Double getFuelCostAll() {
        return this.fuelCostAll;
    }

    public void setFuelCostAll(Double fuelCostAll) {
        this.fuelCostAll = fuelCostAll;
    }

    public Double getFuelCostPerKM() {
        return this.fuelCostPerKM;
    }

    public void setFuelCostPerKM(Double fuelCostPerKM) {
        this.fuelCostPerKM = fuelCostPerKM;
    }

    public Double getFuelEnd() {
        return this.fuelEnd;
    }

    public void setFuelEnd(Double fuelEnd) {
        this.fuelEnd = fuelEnd;
    }

    public String getFuelGet() {
        return this.fuelGet;
    }

    public void setFuelGet(String fuelGet) {
        this.fuelGet = fuelGet;
    }

    public Double getFuelStart() {
        return this.fuelStart;
    }

    public void setFuelStart(Double fuelStart) {
        this.fuelStart = fuelStart;
    }

    public Long getIDDriver() {
        return this.IDDriver;
    }

    public void setIDDriver(Long IDDriver) {
        this.IDDriver = IDDriver;
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

    public Double getLon() {
        return this.lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getStop() {
        return this.stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getTank() {
        return this.tank;
    }

    public void setTank(String tank) {
        this.tank = tank;
    }

    public List<Geopoint> getGeopoints() {
        return this.geopoints;
    }

    public void setGeopoints(List<Geopoint> geopoints) {
        this.geopoints = geopoints;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public SystemUser getSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Stop removeStop(Stop stop) {
        getStops().remove(stop);
        stop.setRoute(null);

        return stop;
    }

    public List<Tank> getTanks() {
        return this.tanks;
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    public String getTimePeriodString() {
        return timePeriodString;
    }

    public void setTimePeriodString(String timePeriodString) {
        this.timePeriodString = timePeriodString;
    }
}
