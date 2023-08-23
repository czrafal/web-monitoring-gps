package com.multisoftware.repository;

import com.multisoftware.model.GpsDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GpsDevicesRepository extends JpaRepository <GpsDevice, Long> {

    @Query("Select g from GpsDevice g where g.imei = :imei")
    GpsDevice getGpsDeviceByImei(@Param("imei") String imei);

    @Query("Select g from GpsDevice g where g.IDDevice = :iddevice")
    GpsDevice getGpsDeviceById(@Param("iddevice") Long iddevice);

}
