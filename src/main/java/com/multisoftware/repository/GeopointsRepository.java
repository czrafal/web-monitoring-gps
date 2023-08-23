package com.multisoftware.repository;

import com.multisoftware.model.Geopoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GeopointsRepository extends JpaRepository<Geopoint, Long> {

    @Query("SELECT g from Geopoint g where g.IDSystem = :IDSystem and g.IDRoutes = :IDRoute")
    List<Geopoint> findByIDRoute(@Param("IDSystem") Long idSystem, @Param("IDRoute") Long IDRoute);

    @Query("SELECT g from Geopoint g where g.IDSystem =:IDSystem and g.IDRoutes=:IDRoutes and (EXTRACT(HOUR FROM g.dateTime) * 60 + EXTRACT(MINUTE FROM g.dateTime)) BETWEEN :timeFrom and :timeTo")
    List<Geopoint> findByIDRouteAndTime(@Param("IDSystem") Long idSystem, @Param("IDRoutes") Long IDRoutes, @Param("timeFrom") int timeFrom, @Param("timeTo") int timeTo);

    @Query
    List<Geopoint> findByIDRoute(@Param("IDSystem") Long idSystem, @Param("IDRoutes") Long IDRoutes, @Param("timeFrom") int timeFrom, @Param("timeTo") int timeTo);

}
