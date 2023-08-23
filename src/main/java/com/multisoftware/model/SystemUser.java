package com.multisoftware.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "SystemUsers" database table.
 *
 */
@Entity
@Table(name="systemusers", schema="public")
@NamedQuery(name="SystemUser.findAll", query="SELECT s FROM SystemUser s")
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsystem")
    private Long IDSystem;

    //bi-directional many-to-one association to Driver
    @OneToMany(mappedBy = "systemUser")
    private List<Driver> drivers;

    //bi-directional many-to-one association to Geopoint
    @OneToMany(mappedBy = "systemUser")
    private List<Geopoint> geopoints;

    //bi-directional many-to-one association to Route
    @OneToMany(mappedBy = "systemUser")
    private List<Route> routes;

    //bi-directional many-to-one association to Stop
    @OneToMany(mappedBy = "systemUser")
    private List<Stop> stops;

    //bi-directional many-to-one association to SystemUsersClient
    @OneToMany(mappedBy = "systemUser")
    private List<SystemUsersClient> systemUsersClients;

    //bi-directional many-to-one association to Vehicle
    @OneToMany(mappedBy = "systemUser")
    private List<Vehicle> vehicles;

    public SystemUser() {
    }

    public Long getIDSystem() {
        return this.IDSystem;
    }

    public void setIDSystem(Long IDSystem) {
        this.IDSystem = IDSystem;
    }

    public List<Driver> getDrivers() {
        return this.drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Driver addDriver(Driver driver) {
        getDrivers().add(driver);
        driver.setSystemUser(this);

        return driver;
    }

    public Driver removeDriver(Driver driver) {
        getDrivers().remove(driver);
        driver.setSystemUser(null);

        return driver;
    }

    public List<Geopoint> getGeopoints() {
        return this.geopoints;
    }

    public void setGeopoints(List<Geopoint> geopoints) {
        this.geopoints = geopoints;
    }

    public Geopoint addGeopoint(Geopoint geopoint) {
        getGeopoints().add(geopoint);
        geopoint.setSystemUser(this);

        return geopoint;
    }

    public Geopoint removeGeopoint(Geopoint geopoint) {
        getGeopoints().remove(geopoint);
        geopoint.setSystemUser(null);

        return geopoint;
    }

    public List<Route> getRoutes() {
        return this.routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Route addRoute(Route route) {
        getRoutes().add(route);
        route.setSystemUser(this);

        return route;
    }

    public Route removeRoute(Route route) {
        getRoutes().remove(route);
        route.setSystemUser(null);

        return route;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Stop addStop(Stop stop) {
        getStops().add(stop);
        stop.setSystemUser(this);

        return stop;
    }

    public Stop removeStop(Stop stop) {
        getStops().remove(stop);
        stop.setSystemUser(null);

        return stop;
    }

    public List<SystemUsersClient> getSystemUsersClients() {
        return this.systemUsersClients;
    }

    public void setSystemUsersClients(List<SystemUsersClient> systemUsersClients) {
        this.systemUsersClients = systemUsersClients;
    }

    public SystemUsersClient addSystemUsersClient(SystemUsersClient systemUsersClient) {
        getSystemUsersClients().add(systemUsersClient);
        systemUsersClient.setSystemUser(this);

        return systemUsersClient;
    }

    public SystemUsersClient removeSystemUsersClient(SystemUsersClient systemUsersClient) {
        getSystemUsersClients().remove(systemUsersClient);
        systemUsersClient.setSystemUser(null);

        return systemUsersClient;
    }

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        getVehicles().add(vehicle);
        vehicle.setSystemUser(this);

        return vehicle;
    }

    public Vehicle removeVehicle(Vehicle vehicle) {
        getVehicles().remove(vehicle);
        vehicle.setSystemUser(null);

        return vehicle;
    }

}