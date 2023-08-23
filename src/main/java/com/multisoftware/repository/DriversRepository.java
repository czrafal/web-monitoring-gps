package com.multisoftware.repository;

import com.multisoftware.dto.DriverAndVehicle;
import com.multisoftware.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DriversRepository extends JpaRepository<Driver, Long> {

    List<Driver> findAll();

    @Query("SELECT d FROM Driver d WHERE d.IDSystem = :IDSystem")
    List<Driver> getDriversBySystemID(@Param("IDSystem") Long idSystem);

    @Query("Select d.IDDriver, d.IDSystem, v.IDVehicle, d.FName, d.LName, d.phone, v.model, v.brand, v.regNum from Driver d, Vehicle v where v.IDVehicle = d.IDVehicle and d.IDSystem = 2 group by d.IDDriver, v.IDVehicle, v.brand, v.model")
    List<DriverAndVehicle> allDriversAndVehiclesShow();

    @Query("Select d.IDDriver, d.IDSystem, v.IDVehicle,  d.FName, d.LName, d.phone, v.model, v.brand, v.regNum from Driver d, Vehicle v where v.IDVehicle = d.IDVehicle and d.IDSystem = :IDSystem and v.IDVehicle = :idVehicle group by d.IDDriver, v.IDVehicle, v.brand, v.model")
    DriverAndVehicle allDriversAndVehiclesByRegisterNumber(@Param("IDSystem") Long IDSystem, @Param("idVehicle") Long idVehicle);

    @Query("SELECT r, d FROM Route r, Driver d where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')  >= :dateStart and to_date(to_char(r.dateEnd, 'YYYY-MON-DD'), 'YYYY-MON-DD')  <= :dateEnd and r.IDDriver = d.IDDriver")
    Object[] allDriversWorkTimeByDateRange(@Param("IDSystem") Long IDSystem, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);

    @Query("SELECT r, d FROM Route r, Driver d where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')  = :dateStart and r.IDDriver = d.IDDriver")
    List<Object[]> allDriversWorkTimeByDate(@Param("IDSystem") Long IDSystem, @Param("dateStart") Date dateStart);

    @Query("Select v, g, r from Geopoint g RIGHT OUTER JOIN g.vehicle v LEFT JOIN v.gpsDevice d LEFT JOIN g.route r where g.vehicle = v and v.IDSystem = 2 and g.IDGeopoints = (SELECT max(g1.IDGeopoints) from Geopoint g1 where g.vehicle = g1.vehicle) OR g is null and v.gpsDevice = d and r = g.route OR r is null")
    List<Object[]> allDriverRealShow();
}
