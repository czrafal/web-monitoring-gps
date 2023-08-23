package com.multisoftware.service;

import com.multisoftware.dto.*;
import com.multisoftware.model.Driver;
import com.multisoftware.model.Route;
import com.multisoftware.repository.DriversRepository;
import com.multisoftware.repository.VehiclesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Refactor of DriverListBean.
 *
 */

@Service
public class DriverListService {

    private static final Logger logger = LogManager.getLogger(DriverListService.class);
            
    @Autowired
    private DriversRepository driversRepository;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private EntityConverterService EntityConverterService;

    public List<Driver> allDriverShow() {
        return driversRepository.getDriversBySystemID(2L);
    }

    public List<DriverAndVehicle> allDriversAndVehiclesShow(){
        return driversRepository.allDriversAndVehiclesShow();
    }

    public void updateDriver(Driver driver){
        driver.setIDVehicle(driver.getVehicle().getIDVehicle());
        driversRepository.save(driver);
    }

    public Driver removeDriver(Long id){
        Optional<Driver> driver = driversRepository.findById(id);
        Driver driverToRemove = driver.get();
        driversRepository.delete(driverToRemove);
        return driverToRemove;
    }

    public DriverAndVehicle getVehicleByRegNum(Long idSystem, Long idVehicle){
        return driversRepository.allDriversAndVehiclesByRegisterNumber(idSystem, idVehicle);
    }

    public List<RouteDTO> allDriversWorkTimeByDateRange(Date from, Date to){
        java.util.Date parsedDateStart = from;
        java.util.Date parsedDateEnd = to;
        List<Object[]> driverList = Collections.singletonList(driversRepository.allDriversWorkTimeByDateRange(new Long(2), new Date(parsedDateStart.getTime()), new Date(parsedDateEnd.getTime())));

        List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();

        for(Object obj[] : driverList){
            RouteDTO routeDTO = new RouteDTO();
            Route route = (Route) obj[0];
            Driver driver = (Driver) obj[1];
            DriverDTO driverDTO = EntityConverterService.convertDriverEntityToDTO(driver);
            RouteFromEntityDTO routeDto = EntityConverterService.convertRouteEntityToDTO(route);
            routeDTO.setRoute(routeDto);
            routeDTO.setDriver(driverDTO);
            DateTime startTime = new DateTime(route.getDateStart());
            DateTime endTime = new DateTime(route.getDateEnd());
            Period p = new Period(startTime, endTime);
            int hours = p.getHours();
            routeDTO.setRouteDays(p.getDays());
            routeDTO.setRouteHours(hours);
            routeDTO.setRouteMinutes(p.getMinutes());
            routeListDTO.add(routeDTO);
        }
        return routeListDTO;
    }

    public void allDriversWorkTimeByDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        java.util.Date parsedDate = date;
        try {
            parsedDate = dateFormat.parse("2013-04-29 00:00:00");
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        List<Object[]> driverList = driversRepository.allDriversWorkTimeByDate(2l, new Date(parsedDate.getTime()));

        for(Object obj[] : driverList){
            Route route = (Route)obj[0];
            Driver driver = (Driver) obj[1];
            logger.debug(driver.getFName());
            logger.debug(driver.getLName());
            logger.debug(route.getDistanceSum());
            logger.debug(route.getDateStart());
            logger.debug(route.getDateEnd());
        }
    }

    public void addNewDriver(Driver driver){
        driver.setIDSystem(new Long(2));
        driversRepository.save(driver);
    }
}
