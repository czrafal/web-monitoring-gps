package com.multisoftware.model;
import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import java.sql.Timestamp;

@NamedQueries({
        @NamedQuery(name="Geopoint.findAll", query="SELECT g FROM Geopoint g"),
        @NamedQuery(name="Geopoint.findByIDRoute", query="SELECT g from Geopoint g where g.IDSystem =:IDSystem and g.IDRoutes=:IDRoutes"),
//	@NamedQuery(name="Geopoint.findByIDRouteAndTime", query="SELECT g from Geopoint g where g.IDSystem =:IDSystem and g.IDRoutes=:IDRoutes and  CAST(g.dateTime AS TIME) BETWEEN :timeFrom and :timeTo")
//	@NamedQuery(name="Geopoint.findByIDRouteAndTime", query="SELECT g from Geopoint g where g.IDSystem =:IDSystem and g.IDRoutes=:IDRoutes and g.dateTime BETWEEN :timeFrom and :timeTo")
        @NamedQuery(name="Geopoint.findByIDRouteAndTime", query="SELECT g from Geopoint g where g.IDSystem =:IDSystem and g.IDRoutes=:IDRoutes and (EXTRACT(HOUR FROM g.dateTime) * 60 + EXTRACT(MINUTE FROM g.dateTime)) BETWEEN :timeFrom and :timeTo")
})
@SequenceGenerator(name="geopoint_seq1",sequenceName = "geopoint_seq",allocationSize=1)
@Entity
@Table(name="geopoints", schema = "public")
public class Geopoint implements Serializable {
    private static final long serialVersionUID = 1L;

    public static String GET_GEOPOINTS_BY_ROUTE = "Geopoint.findByIDRoute";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="geopoint_seq1")
    @Column(name="idgeopoints")
    private Long IDGeopoints;

    @Column(name="datetime")
    private Timestamp dateTime;

    @Column(name="distance")
    private double distance;

    @Column(name="gas")
    private double gas;

    @Column(name="iddriver")
    private BigInteger IDDriver;

    @Column(name="idroutes")
    private Long IDRoutes;

    @Column(name="idsystem")
    private Long IDSystem;

    @Column(name="idvehicle")
    private Long IDVehicle;

    @Column(name="lat")
    private double lat;

    @Column(name="lon")
    private double lon;

    @Column(name="maxspeed")
    private Integer maxspeed;

    //bi-directional many-to-one association to Driver
    @ManyToOne
    @JoinColumn(name="iddriver", referencedColumnName="iddriver", insertable=false, updatable=false)
    private Driver driver;

    //bi-directional many-to-one association to Route
    @ManyToOne
    @JoinColumn(name="idroutes", referencedColumnName="idroutes", insertable=false, updatable=false)
    private Route route;

    //bi-directional many-to-one association to SystemUser
    @ManyToOne
    @JoinColumn(name="idsystem", referencedColumnName="idsystem", insertable=false, updatable=false)
    private SystemUser systemUser;

    //bi-directional many-to-one association to Vehicle
    @ManyToOne
    @JoinColumn(name="idvehicle", referencedColumnName="idvehicle", insertable=false, updatable=false)
    private Vehicle vehicle;

    public Geopoint() {
    }

    public Long getIDGeopoints() {
        return this.IDGeopoints;
    }

    public void setIDGeopoints(Long IDGeopoints) {
        this.IDGeopoints = IDGeopoints;
    }

    public Timestamp getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getGas() {
        return this.gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

    public BigInteger getIDDriver() {
        return this.IDDriver;
    }

    public void setIDDriver(BigInteger IDDriver) {
        this.IDDriver = IDDriver;
    }

    public Long getIDRoutes() {
        return this.IDRoutes;
    }

    public void setIDRoutes(Long IDRoutes) {
        this.IDRoutes = IDRoutes;
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

    public Integer getMaxspeed() {
        return this.maxspeed;
    }

    public void setMaxspeed(Integer maxspeed) {
        this.maxspeed = maxspeed;
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