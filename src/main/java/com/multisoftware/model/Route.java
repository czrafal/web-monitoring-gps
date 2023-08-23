package com.multisoftware.model;
import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@NamedQueries({
        @NamedQuery(name="Route.getRouteByDay",
                query="SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart"),
        @NamedQuery(name="Route.getRouteByDayAndVehicle",
                query="SELECT r FROM Route r where r.IDSystem = :IDSystem and r.IDVehicle = :idVehicle and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart"),
        @NamedQuery(name="Route.getRouteByDayAndDriver",
                query="SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart and r.driver.LName LIKE :LName"),
        @NamedQuery(name="Route.getRouteByDayAndVehicleReg",
                query="SELECT r FROM Route r, Vehicle v where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart and v.regNum = :regNumber and v.IDVehicle = r.IDVehicle"),
        @NamedQuery(name="Route.getRouteByDateRange",
                query="SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') between :dateStart and :dateEnd"),
        @NamedQuery(name="Route.getRouteByDayRangeAndReg",
                query="SELECT r FROM Route r, Vehicle v where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') between :dateStart and :dateEnd and v.regNum = :regNumber and v.IDVehicle = r.IDVehicle"),
        @NamedQuery(name="Route.getRouteByMonthAndYear",
                query="SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON'), 'YYYY-MON') = :dateStart"),
        @NamedQuery(name="Route.getRouteByMonthAndYearAndReg",
                query="SELECT r FROM Route r, Vehicle v where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON'), 'YYYY-MON') = :dateStart and v.regNum = :regNumber and v.IDVehicle = r.IDVehicle"),
        @NamedQuery(name="Route.getRouteByDaySummary",
                query="SELECT r, min(to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')), max(to_date(to_char(r.dateEnd, 'YYYY-MON-DD'), 'YYYY-MON-DD')), sum(r.fuelEnd) as fuelSum, sum(r.distanceSum) as distanceSum FROM Route r, Driver d where r.IDSystem =:IDSystem and d.IDDriver = r.IDDriver and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :date group by r.IDRoutes"),
        @NamedQuery(name="Route.getRouteByMonthAndDriver",
                query="SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON'), 'YYYY-MON') = :dateStart and r.driver.LName LIKE :LName"),
        @NamedQuery(name="Route.getTimeByDateRangeAndDriver",
                query="Select r, min(to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')), max(to_date(to_char(r.dateEnd, 'YYYY-MON-DD'), 'YYYY-MON-DD')), sum(r.fuelEnd) as fuelSum, sum(r.distanceSum) as distanceSum FROM Route r, Driver d where r.IDSystem =:IDSystem and d.IDDriver = r.IDDriver and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') between :dateStart and :dateEnd and r.driver.LName LIKE :LName group by r.IDRoutes")
})

@SequenceGenerator(name="routes_seq1",sequenceName = "routes_seq",allocationSize=1)
@Entity
@Table(name="routes", schema = "public")
@NamedQuery(name="Route.findAll", query="SELECT r FROM Route r")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    public static String GET_ROUTE_BY_DAY = "Route.getRouteByDay";
    public static String GET_ROUTE_BY_DAY_AND_VEHICLE = "Route.getRouteByDayAndVehicle";
    public static String GET_ROUTE_BY_DAY_AND_DRIVER = "Route.getRouteByDayAndDriver";
    public static String GET_ROUTE_BY_DAY_AND_VEHICLE_REG = "Route.getRouteByDayAndVehicleReg";
    public static String GET_ROUTE_BY_DATE_RANGE = "Route.getRouteByDateRange";
    public static String GET_ROUTE_BY_DAY_SUMMARY = "Route.getRouteByDaySummary";
    public static String GET_ROUTE_BY_MONTH_AND_YEAR = "Route.getRouteByMonthAndYear";
    public static String GET_ROUTE_BY_MONTH_AND_YEAR_AND_REG = "Route.getRouteByMonthAndYearAndReg";
    public static String GET_ROUTE_BY_MONTH_AND_DRIVER = "Route.getRouteByMonthAndDriver";
    public static String GET_ROUTE_BY_DAY_RANGE_AND_REG = "Route.getRouteByDayRangeAndReg";
    public static String GET_WORKTIME_BY_DAY_RANGE_AND_NAME = "Route.getTimeByDateRangeAndDriver";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "routes_seq1")
    @Column(name = "idroutes")
    private Long IDRoutes;

    @Column(name = "dateend")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    @Column(name = "datestart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;

    @Column(name = "distancesum")
    private Double distanceSum;

    @Column(name = "fuelcostall")
    private Double fuelCostAll;

    @Column(name = "fuelcostperkm")
    private Double fuelCostPerKM;

    @Column(name = "fuelend")
    private Double fuelEnd;

    @Column(name = "fuelget", columnDefinition = "bpchar(1)")
    private String fuelGet;

    @Column(name = "fuelstart")
    private Double fuelStart;

    @Column(name = "iddriver")
    private Long IDDriver;

    @Column(name = "idsystem")
    private Long IDSystem;

    @Column(name = "idvehicle")
    private Long IDVehicle;

    @Column(nullable = true, name = "lat")
    private Double lat;

    @Column(nullable = true, name = "lon")
    private Double lon;

    @Column(name = "stop", columnDefinition = "bpchar(1)")
    private String stop;

    @Column(name = "tank", columnDefinition = "bpchar(1)")
    private String tank;

    //bi-directional many-to-one association to Geopoint
    @OneToMany(mappedBy = "route")
    private List<Geopoint> geopoints;

    //bi-directional many-to-one association to Driver
    @ManyToOne
    @JoinColumn(name = "iddriver", referencedColumnName = "iddriver", insertable = false, updatable = false)
    private Driver driver;

    //bi-directional many-to-one association to SystemUser
    @ManyToOne
    @JoinColumn(name = "idsystem", referencedColumnName = "idsystem", insertable = false, updatable = false)
    private SystemUser systemUser;

    //bi-directional many-to-one association to Vehicle
    @ManyToOne
    @JoinColumn(name = "idvehicle", referencedColumnName = "idvehicle", insertable = false, updatable = false)
    private Vehicle vehicle;

    //bi-directional many-to-one association to Stop
    @OneToMany(mappedBy = "route")
    private List<Stop> stops;

    //bi-directional many-to-one association to Tank
    @OneToMany(mappedBy = "route")
    private List<Tank> tanks;

    @Transient
    private String timePeriodString;

    public Route() {
    }

    public Long getIDRoutes() {
        return this.IDRoutes;
    }

    public void setIDRoutes(Long IDRoutes) {
        this.IDRoutes = IDRoutes;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
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

    public Geopoint addGeopoint(Geopoint geopoint) {
        getGeopoints().add(geopoint);
        geopoint.setRoute(this);

        return geopoint;
    }

    public Geopoint removeGeopoint(Geopoint geopoint) {
        getGeopoints().remove(geopoint);
        geopoint.setRoute(null);

        return geopoint;
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

    public Stop addStop(Stop stop) {
        getStops().add(stop);
        stop.setRoute(this);

        return stop;
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

    public Tank addTank(Tank tank) {
        getTanks().add(tank);
        tank.setRoute(this);

        return tank;
    }

    public Tank removeTank(Tank tank) {
        getTanks().remove(tank);
        tank.setRoute(null);

        return tank;
    }

    public String getTimePeriodString() {
        return timePeriodString;
    }

    public void setTimePeriodString(String timePeriodString) {
        this.timePeriodString = timePeriodString;
    }

}