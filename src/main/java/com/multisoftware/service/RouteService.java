package com.multisoftware.service;

import com.multisoftware.dto.*;
import com.multisoftware.model.Driver;
import com.multisoftware.model.Geopoint;
import com.multisoftware.model.Route;
import com.multisoftware.model.Vehicle;
import com.multisoftware.repository.GeopointsRepository;
import com.multisoftware.repository.RoutesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

@Service
public class RouteService {

    private static final Logger logger = LogManager.getLogger(RouteService.class);

    @Autowired
    private EntityConverterService entityConverterService;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private GeopointsRepository geopointsRepository;

    public List<RouteDTO> getRouteByDayAndDriver(Date date, String search) {
        List<Route> routeList = routesRepository.getRouteByDayAndDriver(new Long(2), new Timestamp(date.getTime()), '%' + search + '%');
        return entityConverterService.convertToList(routeList);
    }

    public List<RouteDTO> getRouteByMonthAndDriver(Date date, String search) {
        List<Route> routeList = routesRepository.getRouteByMonthAndDriver(new Long(2), new Timestamp(date.getTime()), '%' + search + '%');
        return entityConverterService.convertToList(routeList);
    }

    @SuppressWarnings("unchecked")
    public List<WorkTimeDTO> getWorkTimeByDayRangeAndDriver(Date dateStart, Date dateEnd, String name) {

        if (dateStart == null && dateEnd == null) {
            return new ArrayList<>();
        }

        List<WorkTimeDTO> workTimeDTOList = new ArrayList<>();
        List<WorkTimeDTO> workTimeDTORetList = new ArrayList<>();
        Map<DriverDTO, WorkTimeDTO> retMap = new HashMap<>();

        List<Object[]> workTimeObjList = routesRepository.getWorkTimeByDateRangeAndName(Long.valueOf(2), new Date(dateStart.getTime()), new Date(dateEnd.getTime()), name.trim());

            for (Object obj[] : workTimeObjList) {
                WorkTimeDTO workTimeDTO = new WorkTimeDTO();
                Route route = (Route) obj[0];
                RouteFromEntityDTO routeFromEntity = entityConverterService
                        .convertRouteEntityToDTO(route);
                DriverDTO driver = entityConverterService.convertDriverEntityToDTO(route.getDriver());
                Date datestart = (Date) obj[1];
                Date dateend = (Date) obj[2];
                Double fuelSum = (Double) obj[3];
                Double distanceSum = (Double) obj[4];
                workTimeDTO.setRoute(routeFromEntity);
                workTimeDTO.setDriver(driver);
                workTimeDTO.setDateStart(datestart);
                workTimeDTO.setDateEnd(dateend);
                workTimeDTO.setDistanceSum(distanceSum);
                // workTimeDTO.setFuelEnd(fuelSum);
                workTimeDTOList.add(workTimeDTO);
                if (retMap.containsKey(driver)) {
                    WorkTimeDTO newDTO = retMap.get(driver);
                    newDTO.setDistanceSum(distanceSum + newDTO.getDistanceSum());
                    // newDTO.setFuelEnd(fuelSum+newDTO.getFuelEnd());
                    retMap.put(driver, newDTO);
                } else {
                    retMap.put(driver, workTimeDTO);
                }

            }

        workTimeDTORetList = new ArrayList<>(retMap.values());
        return workTimeDTORetList;
    }

    public List<RouteDTO> getRouteByDayAndRegNum(Date date, String search) {
        List<Route> routeList = routesRepository.getRouteByDayAndVehicleReg(new Long(2), new Timestamp(date.getTime()), search.trim());
        return entityConverterService.convertRouteListToRouteDTOList(routeList);
    }

    /*
     * Filtrowanie trasy po dacie i numerze rejestracyjnym - pierwsza wersja
     */
    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByDayAndRegNumBackup(Date date, String search) {
        List<Route> routeList = new ArrayList<Route>();
        List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();

        routesRepository.getRouteByDayAndVehicleReg(new Long(2), new Timestamp(date.getTime()), search.trim());

        for (Route route : routeList) {
            RouteDTO routeDTO = new RouteDTO();
            DriverDTO driver = entityConverterService.convertToSimpleDriverDTO(route.getDriver());
            RouteFromEntityDTO routeFromEntity = entityConverterService.convertRouteEntityToDTO(route);
            routeDTO.setRoute(routeFromEntity);
            routeDTO.setDriver(driver);
            routeListDTO.add(routeDTO);
        }

        return routeListDTO;
    }

    public Map<DriverDTO, List<RouteFromEntityDTO>> routeListDTOToRouteMap (List<RouteDTO> routeListDTO) {
        Map<DriverDTO, List<RouteFromEntityDTO>> routeMap = new HashMap<DriverDTO, List<RouteFromEntityDTO>>();

        for (RouteDTO route : routeListDTO) {
            RouteDTO routeDTO = new RouteDTO();
            RouteFromEntityDTO routeFromEntity = routeDTO.getRoute();
            DriverDTO driver = route.getDriver();
            routeDTO.setDriver(driver);
            routeDTO.setRoute(routeFromEntity);

            if (routeMap.containsKey(driver)) {
                List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
                routeListMap.add(routeFromEntity);
                routeMap.put(driver, routeListMap);
            } else {
                List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
                routeListMap.add(routeFromEntity);
                routeMap.put(driver, routeListMap);
            }
        }

        return routeMap;
    }

    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByDay(Date date) {

        if (date == null) {
            return Collections.emptyList();
        }

        List<Route> routeList = routesRepository.getRouteByDay(Long.valueOf(2), new Date(date.getTime()));
        List<RouteDTO> routesDTO = entityConverterService.convertRouteListToRouteDTOList(routeList);

        Stream<RouteDTO> routesStream = routesDTO.stream();
        Map<Long, Double> summingMap = routesStream.collect(Collectors.groupingBy(RouteDTO::getIDVehicle, Collectors.summingDouble(RouteDTO::getDistance)));

        for (Map.Entry<Long, Double> summingMapEntry : summingMap.entrySet()) {
            Long idVehicle = summingMapEntry.getKey();
            for (RouteDTO routeDTO : routesDTO) {
                if (routeDTO.getIDVehicle().equals(idVehicle)) {
                    routeDTO.setDistanceSum(summingMapEntry.getValue());
                }
            }
        }

        return routesDTO;
    }

    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByMonthAndYear(Date date) {
        List<Route> routeList = new ArrayList<>();
        List<RouteDTO> routeListDTO = new ArrayList<>();

        if (date != null) {
            routeList = routesRepository.getRouteByMonthAndYear(Long.valueOf(2), new Date(date.getTime()));
            routeListDTO = routeConverter(routeList);
        }

        return routeListDTO;
    }

    /*
     * Filtrowanie tras po miesiacu, roku, i nr rejestracji
     */
    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByMonthAndYearAndRegNum(Date date, String reg) {
        List<Route> routeList = new ArrayList<>();
        List<RouteDTO> routeListDTO = new ArrayList<>();

        if (date != null) {
            routeList = routesRepository.getRouteByMonthAndYearAndReg(Long.valueOf(2), new Date(date.getTime()), reg.trim());
            routeListDTO = routeConverter(routeList);
        }

        return routeListDTO;
    }

    @SuppressWarnings("unchecked")
    public Map<DriverDTO, RouteDTO> getRouteByDayAsMap(Date date) {
        List<Route> routeList = new ArrayList<Route>();
        Map<DriverDTO, RouteDTO> routeMap = new HashMap<DriverDTO, RouteDTO>();

        if (date != null) {
            routeList = routesRepository.getRouteByDay(Long.valueOf(2), new Date(date.getTime()));

            for (Route route : routeList) {
                RouteDTO routeDTO = new RouteDTO();
                DriverDTO driverDTO = entityConverterService.convertToSimpleDriverDTO(route.getDriver());
                routeDTO.setDriver(driverDTO);
                RouteFromEntityDTO routeFromEntity = entityConverterService.convertRouteEntityToDTO(route);
                routeDTO.setRoute(routeFromEntity);
                routeMap.put(driverDTO, routeDTO);
            }
        }

        return routeMap;
    }

    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByDayDetails(Date date) {
        List<Route> routeList = new ArrayList<Route>();
        List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
        List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
        Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<>();

        if (date != null) {
            routeList = routesRepository.getRouteByDay(Long.valueOf(2), new Date(date.getTime()));

            for (Route route : routeList) {
                DriverDTO driverDTO = entityConverterService.convertToSimpleDriverDTO(route.getDriver());
                VehicleDTO vehicleDTO = entityConverterService.convertToSimpleVehicleDTO(route.getVehicle());
                RouteDTO routeDTO = new RouteDTO(driverDTO, vehicleDTO);
                RouteFromEntityDTO routeFromEntity = entityConverterService.convertRouteEntityToDTO(route);
                routeDTO.setRoute(routeFromEntity);
                routeListDTO.add(routeDTO);
            }

            // TODO : implement summing
        }

        return routeListDTO;
    }

    @SuppressWarnings("unchecked")
    public List<RouteDTO> routeConverter(List<Route> routeList) {
        List<RouteDTO> routeListRetDTO = new ArrayList<>();

        if (routeList.isEmpty()) {
            return routeListRetDTO;
        }

        List<RouteDTO> routeListDTO = new ArrayList<>();
        Map<Vehicle, List<RouteFromEntityDTO>> routeMap = new HashMap<>();

        PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
                .appendDays().appendSuffix(" day", "d").appendSeparator(" ")
                .appendHours().appendSuffix(" hour", "h").appendSeparator(" ")
                .appendMinutes().appendSuffix(" minute", "m")
                .appendSeparator(" ").appendSeconds()
                .appendSuffix(" second", "s").toFormatter();

        for (Route route : routeList) {
            RouteFromEntityDTO routeFromEntity = entityConverterService.convertRouteEntityToDTO(route);
            RouteDTO routeDTO = entityConverterService.convertAndPackRouteAndVehicle(route.getDriver(), route.getVehicle());
            routeDTO.setRoute(routeFromEntity);
            routeListDTO.add(routeDTO);
        }

        for (RouteDTO routeDTO : routeListDTO) {
            RouteFromEntityDTO route = routeDTO.getRoute();
            Vehicle vehicle = route.getVehicle();
            if (route.getDateStart() != null && route.getDateEnd() != null) {
                Period period = new Period(new DateTime(route.getDateStart()), new DateTime(route.getDateEnd()));
                logger.debug(daysHoursMinutes.print(period));
                route.setTimePeriodString(daysHoursMinutes.print(period));
            }
            if (routeMap.containsKey(vehicle)) {
                List<RouteFromEntityDTO> routeListMap = routeMap.get(vehicle);
                routeListMap.add(route);
                routeMap.put(vehicle, routeListMap);
            } else {
                List<RouteFromEntityDTO> routeListMap = new ArrayList<>();
                routeListMap.add(route);
                routeMap.put(vehicle, routeListMap);
            }
        }

        for (Vehicle vehicle : routeMap.keySet()) {
            RouteDTO routeDTO = new RouteDTO();
            VehicleDTO vehicleDTO = entityConverterService.convertToSimpleVehicleDTO(vehicle);
            routeDTO.setVehicle(vehicleDTO);
            List<RouteFromEntityDTO> routesList = routeMap.get(vehicle);
            Double distance = new Double(0);

            for (RouteFromEntityDTO route : routesList) {
                if (route.getDateStart() != null && route.getDateEnd() != null) {
                    routeDTO.setDateStart(route.getDateStart());
                    routeDTO.setDateEnd(route.getDateEnd());
                }
                if (route.getDistanceSum() != null) {
                    distance += route.getDistanceSum();
                }
            }

            routeDTO.setDistanceSum(distance);
            routeDTO.setRouteList(routesList);
            routeListRetDTO.add(routeDTO);
        }

        return routeListRetDTO;
    }

    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByDateRange(Date dateStart, Date dateEnd) {
        List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();

        if (dateStart == null || dateEnd == null) {
            return Collections.emptyList();
        }

        List<Route> routeList = routesRepository.getRouteByDateRange(Long.valueOf(2), new Date(dateStart.getTime()), new Date(dateEnd.getTime()));
        for (Route route : routeList) {
            RouteDTO routeDto = entityConverterService.convertRouteToDTO(route);
            routeListDTO.add(routeDto);
        }

        return routeListDTO;
    }

    @SuppressWarnings("unchecked")
    public List<RouteDTO> getRouteByDayRangeAndRegNum(Date dateStart, Date dateEnd, String regNum) {
        List<RouteDTO> routeListRetDTO = new ArrayList<>();

        if (dateStart != null && dateEnd != null) {
            List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
            Map<DriverDTO, List<RouteFromEntityDTO>> routeMap = new HashMap<DriverDTO, List<RouteFromEntityDTO>>();
            List<Route> routeList = routesRepository.getRouteByDayRangeAndReg(Long.valueOf(2), new Date(dateStart.getTime()), new Date(dateEnd.getTime()), regNum.trim());

            for (int i = 0; i < routeList.size(); i++) {
                RouteDTO routeDTO = new RouteDTO();
                Route route = routeList.get(i);
                RouteFromEntityDTO routeFromEntityDTO = entityConverterService.convertRouteEntityToDTO(route);
                DriverDTO driver = entityConverterService.convertToSimpleDriverDTO(route.getDriver());
                routeDTO.setDriver(driver);
                routeDTO.setRoute(routeFromEntityDTO);
                routeListDTO.add(routeDTO);
            }

            for (int i = 0; i < routeListDTO.size(); i++) {
                RouteDTO routeDTO = new RouteDTO();
                RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
                DriverDTO driver = entityConverterService.convertToSimpleDriverDTO(route.getDriver());
                routeDTO.setDriver(driver);
                routeDTO.setRoute(route);

                if (routeMap.containsKey(driver)) {
                    List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
                    routeListMap.add(route);
                    routeMap.put(driver, routeListMap);
                } else {
                    List<RouteFromEntityDTO> routeListMap = new ArrayList<>();
                    routeListMap.add(route);
                    routeMap.put(driver, routeListMap);
                }
            }

            for (DriverDTO driver : routeMap.keySet()) {
                RouteDTO ret = new RouteDTO();
                ret.setDriver(driver);
                List<RouteFromEntityDTO> routesList = routeMap.get(driver);
                ret.setRouteList(routesList);
                routeListRetDTO.add(ret);
            }
        }

        return routeListRetDTO;
    }

    public List<WorkTimeDTO> getWorkTime(Date start, Date end, boolean detail) {
        Map<VehicleDTO, WorkTimeDTO> retMap = new HashMap<>();

        if (start != null && end == null) {
            List<Object[]> workTimeObjList = routesRepository.getRouteByDaySummary(Long.valueOf(2), new Date(start.getTime()));
            for (Object obj[] : workTimeObjList) {
                WorkTimeDTO workTimeDTO = new WorkTimeDTO();
                Route route = (Route) obj[0];
                RouteFromEntityDTO routeFromEntity = entityConverterService.convertRouteEntityToDTO(route);
                DriverDTO driver = entityConverterService.convertToSimpleDriverDTO(route.getDriver());
                Date dateStart = (Date) obj[1];
                Date dateEnd = (Date) obj[2];
                Double fuelSum = (Double) obj[3];
                Double distanceSum = (Double) obj[4];
                workTimeDTO.setRoute(routeFromEntity);
                workTimeDTO.setDriver(driver);
                workTimeDTO.setDateStart(dateStart);
                workTimeDTO.setDateEnd(dateEnd);
                workTimeDTO.setDistanceSum(distanceSum);
                workTimeDTO.setFuelEnd(fuelSum);
                VehicleDTO vehicle = entityConverterService.convertToSimpleVehicleDTO(route.getVehicle());
                workTimeDTO.setVehicle(vehicle);
                if (retMap.containsKey(vehicle)) {
                    WorkTimeDTO newDTO = retMap.get(vehicle);
                    newDTO.setDistanceSum(distanceSum + newDTO.getDistanceSum());
                    newDTO.setFuelEnd(fuelSum + newDTO.getFuelEnd());
                    retMap.put(vehicle, newDTO);
                } else {
                    retMap.put(vehicle, workTimeDTO);
                }
            }
        }

        return new ArrayList<>(retMap.values());
    }

    public List<Geopoint> getGeopointsForRoute(long idRoute) {
        return geopointsRepository.findByIDRoute(Long.valueOf(2), idRoute);
    }

    public Route getLastRouteByDevice(long device) {
        return routesRepository.getLastRouteByDevice(device);
    }
}
