package com.multisoftware.repository;

import com.multisoftware.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface RoutesRepository extends JpaRepository<Route, Long> {

    @Query("SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart")
    List<Route> getRouteByDay(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart);

    @Query("SELECT r FROM Route r where r.IDSystem = :IDSystem and r.IDVehicle = :idVehicle and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart")
    List<Route> getRouteByDayAndVehicle(@Param("IDSystem") Long idSystem, @Param("dateStart") String dateStart, @Param("idVehicle") Long idVehicle);

    @Query("SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart and r.driver.LName LIKE :LName")
    List<Route> getRouteByDayAndDriver(@Param("IDSystem") Long idSystem, @Param("dateStart") Timestamp dateStart, @Param("LName") String lName);

    @Query("SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON'), 'YYYY-MON') = :dateStart and r.driver.LName LIKE :LName")
    List<Route> getRouteByMonthAndDriver(@Param("IDSystem") Long idSystem, @Param("dateStart") Timestamp dateStart, @Param("LName") String lName);

    @Query("Select r, min(to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')), max(to_date(to_char(r.dateEnd, 'YYYY-MON-DD'), 'YYYY-MON-DD')), sum(r.fuelEnd) as fuelSum, sum(r.distanceSum) as distanceSum FROM Route r, Driver d where r.IDSystem =:IDSystem and d.IDDriver = r.IDDriver and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') between :dateStart and :dateEnd and r.driver.LName LIKE :LName group by r.IDRoutes")
    List<Object[]> getWorkTimeByDateRangeAndName(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd, @Param("LName") String lName);

    @Query("SELECT r FROM Route r, Vehicle v where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :dateStart and v.regNum = :regNumber and v.IDVehicle = r.IDVehicle")
    List<Route> getRouteByDayAndVehicleReg(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart, @Param("regNumber") String regNumber);

    @Query("SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON'), 'YYYY-MON') = :dateStart")
    List<Route> getRouteByMonthAndYear(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart);

    @Query("SELECT r FROM Route r, Vehicle v where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON'), 'YYYY-MON') = :dateStart and v.regNum = :regNumber and v.IDVehicle = r.IDVehicle")
    List<Route> getRouteByMonthAndYearAndReg(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart, @Param("regNumber") String regNumber);

    @Query("SELECT r FROM Route r where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') between :dateStart and :dateEnd")
    List<Route> getRouteByDateRange(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);

    @Query("SELECT r FROM Route r, Vehicle v where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') between :dateStart and :dateEnd and v.regNum = :regNumber and v.IDVehicle = r.IDVehicle")
    List<Route> getRouteByDayRangeAndReg(@Param("IDSystem") Long idSystem, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd, @Param("regNumber") String regNumber);

    @Query("SELECT r, min(to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')), max(to_date(to_char(r.dateEnd, 'YYYY-MON-DD'), 'YYYY-MON-DD')), sum(r.fuelEnd) as fuelSum, sum(r.distanceSum) as distanceSum FROM Route r, Driver d where r.IDSystem =:IDSystem and d.IDDriver = r.IDDriver and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD') = :date group by r.IDRoutes")
    List<Object[]> getRouteByDaySummary(@Param("IDSystem") Long idSystem, @Param("date") Date date);

    @Query("Select r from Geopoint g RIGHT OUTER JOIN g.vehicle v LEFT JOIN v.gpsDevice d LEFT JOIN g.route r where g.vehicle = v and v.IDSystem = 2 and g.IDGeopoints = (SELECT max(g1.IDGeopoints) from Geopoint g1 where g.vehicle = g1.vehicle) OR g is null and v.gpsDevice = d and r = g.route OR r is null and v.gpsDevice.IDDevice = :iddevice")
    Route getLastRouteByDevice(@Param("iddevice") Long iddevice);
}
