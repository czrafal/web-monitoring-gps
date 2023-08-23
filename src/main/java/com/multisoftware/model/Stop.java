package com.multisoftware.model;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Time;


@Entity
@Table(name="stop", schema = "public")
@NamedQuery(name="Stop.findAll", query="SELECT s FROM Stop s")
public class Stop implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstop")
    private Long IDStop;

    @Column(name = "datetime")
    private Timestamp dateTime;

    @Column(name = "iddriver")
    private Long IDDriver;

    @Column(name = "idroute")
    private Long IDRoute;

    @Column(name = "idsystem")
    private Long IDSystem;

    @Column(name = "idvehicle")
    private Long IDVehicle;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    @Column(name = "time")
    private Time time;

    //bi-directional many-to-one association to Driver
    @ManyToOne
    @JoinColumn(name = "iddriver", referencedColumnName = "iddriver", insertable = false, updatable = false)
    private Driver driver;

    //bi-directional many-to-one association to Route
    @ManyToOne
    @JoinColumn(name = "idroute", referencedColumnName = "idroutes", insertable = false, updatable = false)
    private Route route;

    //bi-directional many-to-one association to SystemUser
    @ManyToOne
    @JoinColumn(name = "idsystem", referencedColumnName = "idsystem", insertable = false, updatable = false)
    private SystemUser systemUser;

    //bi-directional many-to-one association to Vehicle
    @ManyToOne
    @JoinColumn(name = "idvehicle", referencedColumnName = "idvehicle", insertable = false, updatable = false)
    private Vehicle vehicle;

    public Stop() {
    }

    public Long getIDStop() {
        return this.IDStop;
    }

    public void setIDStop(Long IDStop) {
        this.IDStop = IDStop;
    }

    public Timestamp getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Long getIDDriver() {
        return this.IDDriver;
    }

    public void setIDDriver(Long IDDriver) {
        this.IDDriver = IDDriver;
    }

    public Long getIDRoute() {
        return this.IDRoute;
    }

    public void setIDRoute(Long IDRoute) {
        this.IDRoute = IDRoute;
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

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return this.route;
    }

    public void setRoute(Route route) {
        this.route = route;
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

}