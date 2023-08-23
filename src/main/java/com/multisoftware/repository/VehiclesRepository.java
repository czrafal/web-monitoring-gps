package com.multisoftware.repository;

import com.multisoftware.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiclesRepository extends CrudRepository<Vehicle, Long> {

    @Query("SELECT v from Vehicle v where v.IDSystem = :IDSystem")
    List<Vehicle> allVehiclesByIDSystem(@Param("IDSystem") Long IDSystem);

    @Query("SELECT v FROM Vehicle v where v.IDSystem =:IDSystem and v.IDVehicle = :idVehicle")
    Vehicle getVehicleById(@Param("IDSystem") Long IDSystem, @Param("idVehicle") Long idVehicle);
}
