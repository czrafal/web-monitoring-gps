package com.multisoftware.model;
import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


@SequenceGenerator(name="vehicle_seq1", sequenceName = "vehicle_seq",allocationSize=1)
@Entity
@Table(name="vehicles", schema = "public")
@NamedQuery(name="Vehicle.findAll", query="SELECT v FROM Vehicle v")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="vehicle_seq1")
    @Column(name="idvehicle")
    private Long IDVehicle;

    @Column(name="brand")
    private String brand;

    @Temporal(TemporalType.DATE)
    @Column(name="buydate")
    private Date buyDate;

    @Temporal(TemporalType.DATE)
    @Column(name="checkend")
    private Date checkEnd;

    @Temporal(TemporalType.DATE)
    @Column(name="checkstart")
    private Date checkStart;

    @Column(name="combustion")
    private double combustion;

    @Column(name="idsystem")
    private Long IDSystem;

    @Column(name="model")
    private String model;

    @Temporal(TemporalType.DATE)
    @Column(name="ocdateend")
    private Date OCDateEnd;

    @Temporal(TemporalType.DATE)
    @Column(name="ocdatestart")
    private Date OCDateStart;

    @Column(name="regnum")
    private String regNum;

    //bi-directional many-to-one association to Driver
    @OneToMany(mappedBy="vehicle")
    private List<Driver> drivers;

    //bi-directional many-to-one association to Geopoint
    @OneToMany(mappedBy="vehicle")
    private List<Geopoint> geopoints;

    //bi-directional many-to-one association to Route
    @OneToMany(mappedBy="vehicle")
    private List<Route> routes;

    //bi-directional many-to-one association to Stop
    @OneToMany(mappedBy="vehicle")
    private List<Stop> stops;

    //bi-directional many-to-one association to Tank
    @OneToMany(mappedBy="vehicle1")
    private List<Tank> tanks1;

    //bi-directional many-to-one association to Tank
    @OneToMany(mappedBy="vehicle2")
    private List<Tank> tanks2;

    //bi-directional many-to-one association to SystemUser
    @ManyToOne
    @JoinColumn(name="idsystem", referencedColumnName="idsystem", insertable=false, updatable=false)
    private SystemUser systemUser;

    @OneToOne
    @JoinColumn(name="iddevice", referencedColumnName="iddevice")
    private GpsDevice gpsDevice;

    @Transient
    private String imei;

    public Vehicle() {
    }

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

    public double getCombustion() {
        return this.combustion;
    }

    public void setCombustion(double combustion) {
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

    public List<Driver> getDrivers() {
        return this.drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Driver addDriver(Driver driver) {
        getDrivers().add(driver);
        driver.setVehicle(this);
        return driver;
    }

    public Driver removeDriver(Driver driver) {
        getDrivers().remove(driver);
        driver.setVehicle(null);
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
        geopoint.setVehicle(this);
        return geopoint;
    }

    public Geopoint removeGeopoint(Geopoint geopoint) {
        getGeopoints().remove(geopoint);
        geopoint.setVehicle(null);
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
        route.setVehicle(this);
        return route;
    }

    public Route removeRoute(Route route) {
        getRoutes().remove(route);
        route.setVehicle(null);
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
        stop.setVehicle(this);
        return stop;
    }

    public Stop removeStop(Stop stop) {
        getStops().remove(stop);
        stop.setVehicle(null);
        return stop;
    }

    public List<Tank> getTanks1() {
        return this.tanks1;
    }

    public void setTanks1(List<Tank> tanks1) {
        this.tanks1 = tanks1;
    }

    public Tank addTanks1(Tank tanks1) {
        getTanks1().add(tanks1);
        tanks1.setVehicle1(this);
        return tanks1;
    }

    public Tank removeTanks1(Tank tanks1) {
        getTanks1().remove(tanks1);
        tanks1.setVehicle1(null);
        return tanks1;
    }

    public List<Tank> getTanks2() {
        return this.tanks2;
    }

    public void setTanks2(List<Tank> tanks2) {
        this.tanks2 = tanks2;
    }

    public Tank addTanks2(Tank tanks2) {
        getTanks2().add(tanks2);
        tanks2.setVehicle2(this);
        return tanks2;
    }

    public Tank removeTanks2(Tank tanks2) {
        getTanks2().remove(tanks2);
        tanks2.setVehicle2(null);
        return tanks2;
    }

    public SystemUser getSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public GpsDevice getGpsDevice() {
        return gpsDevice;
    }

    public void setGpsDevice(GpsDevice gpsDevice) {
        this.gpsDevice = gpsDevice;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}