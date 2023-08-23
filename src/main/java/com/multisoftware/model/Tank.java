package com.multisoftware.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="tanks", schema = "public")
@NamedQueries({
        @NamedQuery(name="Tank.findAll", query="SELECT t FROM Tank t"),
        @NamedQuery(name="Tank.findByDate", query="SELECT t FROM Tank t, Route r where t.IDSystem = :IDSystem and r.IDRoutes = t.IDRoute and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart"),
})

public class Tank implements Serializable {
    private static final long serialVersionUID = 1L;

    public static String GET_TANK_BY_DAY = "Tank.findByDate";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="IDTank")
    private Long IDTank;

    @Column(name="IDDriver")
    private Long IDDriver;

    @Column(name="IDRoute")
    private Long IDRoute;

    @Column(name="IDSystem")
    private Long IDSystem;

    @Column(name="IDVehicle")
    private Long IDVehicle;

    @Column(name="Lat")
    private Double lat;

    @Column(name="Litres")
    private Double litres;

    @Column(name="Lon")
    private Double lon;

    @Column(name="Price")
    private double price;

    @ManyToOne
    @JoinColumn(name="IDDriver", referencedColumnName="IDDriver", insertable=false, updatable=false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name="IDRoute", referencedColumnName="IDRoutes", insertable=false, updatable=false)
    private Route route;

    @ManyToOne
    @JoinColumn(name="IDVehicle", referencedColumnName="IDVehicle", insertable=false, updatable=false)
    private Vehicle vehicle1;

    @ManyToOne
    @JoinColumn(name="IDVehicle", referencedColumnName="IDVehicle", insertable=false, updatable=false)
    private Vehicle vehicle2;

    public Tank() {
    }

    public Long getIDTank() {
        return this.IDTank;
    }

    public void setIDTank(Long IDTank) {
        this.IDTank = IDTank;
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

    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLitres() {
        return this.litres;
    }

    public void setLitres(Double litres) {
        this.litres = litres;
    }

    public Double getLon() {
        return this.lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Vehicle getVehicle1() {
        return this.vehicle1;
    }

    public void setVehicle1(Vehicle vehicle1) {
        this.vehicle1 = vehicle1;
    }

    public Vehicle getVehicle2() {
        return this.vehicle2;
    }

    public void setVehicle2(Vehicle vehicle2) {
        this.vehicle2 = vehicle2;
    }

}