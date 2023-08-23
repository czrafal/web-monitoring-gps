package com.multisoftware.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name="Drivers.getAllDrivers",
                query="SELECT d FROM Driver d"),
        @NamedQuery(name="Drivers.getDriversBySystemID",
                query="SELECT d FROM Driver d WHERE d.IDSystem = 2"),
})

@NamedQuery(name="Driver.findAll", query="SELECT d FROM Driver d")
@SequenceGenerator(name="driver_seq1", sequenceName = "driver_seq", allocationSize=1)
@Entity
@Table(name="drivers", schema = "public")
public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String FIND_ALL_DRIVERS = "Drivers.getDriversBySystemID";

    //	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="driver_seq1")
    @Column(name="iddriver")
    private Long IDDriver;

    @Column(name="fname")
    private String FName;

    @Column(name="idsystem")
    private Long IDSystem;

    @Column(name="idvehicle")
    private Long IDVehicle;

    @Column(name="lname")
    private String LName;

    @Column(name="phone")
    private String phone;

    //bi-directional many-to-one association to SystemUser
    @ManyToOne
    @JoinColumn(name="idsystem", referencedColumnName="idsystem", insertable=false, updatable=false)
    private SystemUser systemUser;

    //bi-directional many-to-one association to Vehicle
    @ManyToOne
    @JoinColumn(name="idvehicle", referencedColumnName="idvehicle", insertable=false, updatable=false)
    private Vehicle vehicle;

    //bi-directional many-to-one association to Geopoint
    @OneToMany(mappedBy="driver")
    private List<Geopoint> geopoints;

    //bi-directional many-to-one association to Route
    @OneToMany(mappedBy="driver")
    private List<Route> routes;

    //bi-directional many-to-one association to Stop
    @OneToMany(mappedBy="driver")
    private List<Stop> stops;

    //bi-directional many-to-one association to Tank
    @OneToMany(mappedBy="driver")
    private List<Tank> tanks;

    public Driver() {
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

    public List<Geopoint> getGeopoints() {
        return this.geopoints;
    }

    public void setGeopoints(List<Geopoint> geopoints) {
        this.geopoints = geopoints;
    }

    public Geopoint addGeopoint(Geopoint geopoint) {
        getGeopoints().add(geopoint);
        geopoint.setDriver(this);

        return geopoint;
    }

    public Geopoint removeGeopoint(Geopoint geopoint) {
        getGeopoints().remove(geopoint);
        geopoint.setDriver(null);

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
        route.setDriver(this);

        return route;
    }

    public Route removeRoute(Route route) {
        getRoutes().remove(route);
        route.setDriver(null);

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
        stop.setDriver(this);

        return stop;
    }

    public Stop removeStop(Stop stop) {
        getStops().remove(stop);
        stop.setDriver(null);

        return stop;
    }

    public List<Tank> getTanks() {
        return this.tanks;
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    public Tank addTank(Tank tank) {
        getTanks().add(tank);
        tank.setDriver(this);

        return tank;
    }

    public Tank removeTank(Tank tank) {
        getTanks().remove(tank);
        tank.setDriver(null);

        return tank;
    }

}