package com.multisoftware.service;

import com.multisoftware.model.Geopoint;
import com.multisoftware.model.Route;
import com.multisoftware.repository.GeopointsRepository;
import com.multisoftware.repository.RoutesRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.traccar.model.Position;
import org.traccar.model.VehicleStatusEnum;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class RouteManagerService {

    private static final Logger logger = LogManager.getLogger(RouteManagerService.class);

    @Autowired
    private EntityConverterService entityConverterService;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private GeopointsRepository geopointsRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RouteService routeService;

    public RouteManagerService() {}

    public void savePosition(Position position) {
        try{
            logger.debug("addNewGeopoint!");
            //    	Device device = driversCache.deviceSessionByImei(String.valueOf(position.getDeviceId()));
            long deviceId = 352848022867325l;
            if(position == null){
                logger.debug("addNewGeopoint position is null");
            }
            logger.debug("before get driver");
            Route lastRoute = routeService.getLastRouteByDevice(deviceId);
            logger.debug("addNewGeopoint1!");
            Geopoint geopoint = entityConverterService.convertPositionToGeopointEntity(position);
            logger.debug("addNewGeopoint2!");
            geopoint.setIDDriver(new BigInteger(String.valueOf(lastRoute.getIDDriver())));
            logger.debug("addNewGeopoint3!");
            geopoint.setIDVehicle(lastRoute.getIDVehicle());
            logger.debug("addNewGeopoint4!");
            Route route = checkRouteCreateOrUpdate(position, lastRoute);
            logger.debug("addNewGeopoint5!");
            geopoint.setRoute(route);
            geopoint.setIDRoutes(route.getIDRoutes());
            geopointsRepository.save(geopoint);
            logger.debug("addNewGeopoint6!");
        }catch(Exception e){
            logger.debug("addNewGeopoint! manage error:"+e.getMessage());
        }
    }

    private Route checkRouteCreateOrUpdate(Position position, Route lastRoute){
        logger.debug("before get vehicleStatus");
        VehicleStatusEnum vehicleStatus = position.getVehicleStatus();
        logger.debug("after get vehicle status "+vehicleStatus.name());

        if (lastRoute == null && vehicleStatus == VehicleStatusEnum.IGNITION_ON) { // TODO add column to route check if route ended
            Route newRoute = createRoute(position, lastRoute);
            routesRepository.save(newRoute);
            logger.debug("Create new route id "+ newRoute.getIDRoutes()+ " for device "+lastRoute.getVehicle().getGpsDevice().getIDDevice());
            Geopoint geopoint = new Geopoint();
            geopoint.setIDRoutes(newRoute.getIDRoutes());
            geopoint.setRoute(newRoute);
            return newRoute;
        } else if (lastRoute.getDateStart() != null && lastRoute.getDateEnd() == null) {
            logger.debug("Update route id " + lastRoute.getIDRoutes()+ " for device " + lastRoute.getVehicle().getGpsDevice().getIDDevice());
            if (vehicleStatus == VehicleStatusEnum.IGNITION_OFF) {
                return endRoute(position, lastRoute);
            }
            return lastRoute;
        } else if (lastRoute.getDateStart() != null && lastRoute.getDateEnd() != null) {
            if (vehicleStatus == VehicleStatusEnum.IGNITION_OFF) {
                Route newRoute = createRoute(position, lastRoute);
                routesRepository.save(newRoute);
                logger.debug("Create new route id "+ newRoute.getIDRoutes()+ " for device "+lastRoute.getVehicle().getGpsDevice().getIDDevice());
                return newRoute;
            }
        }
        return lastRoute;
    }

    private Route createRoute(Position position, Route route){
        logger.debug("createRoute");
        Route newRoute = new Route();
        long millis = DateUtils.truncate(position.getDeviceTime(), Calendar.MILLISECOND).getTime();
        Timestamp dateAstimestamp = new java.sql.Timestamp(millis);
        newRoute.setDateStart(dateAstimestamp);
        newRoute.setLat(position.getLatitude());
        newRoute.setLon(position.getLongitude());
        newRoute.setIDVehicle(route.getVehicle().getIDVehicle());
        newRoute.setIDDriver(route.getIDDriver());
        return newRoute;
    }

    private Route endRoute(Position position, Route route) {
        long millis = DateUtils.truncate(position.getDeviceTime(), Calendar.MILLISECOND).getTime();
        Timestamp dateTimestamp = new java.sql.Timestamp(millis);
        route.setDateEnd(dateTimestamp);
        Route endedRoute = routesRepository.save(route);
        return endedRoute;
    }

    public void savePositions(List<Position> positions) {
        positions.forEach(item->savePosition(item));
    }

}
