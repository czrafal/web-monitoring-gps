package com.multisoftware.service;

import com.multisoftware.dto.*;
import com.multisoftware.model.*;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.traccar.model.Device;
import org.traccar.model.Position;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityConverterService {

    private static final Logger logger = LogManager.getLogger(EntityConverterService.class);

    public List<WorkTimeDTO> convertWorkingTimeToDTO(List<Object[]> driverList) {
        List<WorkTimeDTO> workTimeList = new ArrayList<WorkTimeDTO>();
        for (Object obj[] : driverList) {
            WorkTimeDTO dto = new WorkTimeDTO();
            Route route = (Route) obj[0];
            RouteFromEntityDTO routeDto = convertRouteEntityToDTO(route);
            dto.setRoute(routeDto);
            workTimeList.add(dto);
        }
        return workTimeList;
    }

    public RouteFromEntityDTO convertRouteEntityToDTO(Route route) {
        RouteFromEntityDTO routeEntityDTO = new RouteFromEntityDTO();
        PropertyUtilsBean prop = new PropertyUtilsBean();
        try {
            prop.copyProperties(routeEntityDTO, route);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        }
        return routeEntityDTO;
    }

    public List<RouteDTO> convertRouteListToRouteDTOList(List<Route> routeList) {
        List<RouteDTO> routeDTOList = new ArrayList<RouteDTO>(routeList.size());
        for (Route route : routeList) {
            routeDTOList.add(convertRouteToDTO(route));
        }
        return routeDTOList;
    }

    public RouteDTO convertRouteToDTO(Route route) {
        DriverDTO driverDTO = convertToSimpleDriverDTO(route.getDriver());
        VehicleDTO vehicleDTO = convertToSimpleVehicleDTO(route.getVehicle());

        RouteDTO routeDTO = new RouteDTO(driverDTO, vehicleDTO);
        routeDTO.setIDRoutes(route.getIDRoutes());
        routeDTO.setDateStart(route.getDateStart());
        routeDTO.setDateEnd(route.getDateEnd());
        routeDTO.setDistance(route.getDistanceSum());
        return routeDTO;
    }

    public DriverDTO convertDriverEntityToDTO(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();
        PropertyUtilsBean prop = new PropertyUtilsBean();
        try {
            prop.copyProperties(driverDTO, driver);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        }
        return driverDTO;
    }

    public Geopoint convertPositionToGeopointEntity(Position position) {
        Geopoint geopoint = new Geopoint();
        geopoint.setLat(position.getLatitude());
        geopoint.setLon(position.getLongitude());
        Double maxSpeed = position.getSpeed();
        Timestamp timestamp = new Timestamp(position.getDeviceTime().getTime());
        geopoint.setMaxspeed(maxSpeed.intValue());
        geopoint.setDateTime(timestamp);
        geopoint.setIDSystem(2L);
        return geopoint;
    }

    public List<RouteDTO> convertToList(List<Route> routeList) {
        List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
        List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
        Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<Driver, List<RouteFromEntityDTO>>();

        for (int i = 0; i < routeList.size(); i++) {
            RouteDTO routeDTO = new RouteDTO();
            Route route = routeList.get(i);
            RouteFromEntityDTO routeFromEntity = convertRouteEntityToDTO(route);
            routeDTO.setRoute(routeFromEntity);
            routeListDTO.add(routeDTO);
        }

        for (int i = 0; i < routeListDTO.size(); i++) {
            RouteDTO routeDTO = new RouteDTO();
            RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
            Driver driver = route.getDriver();
            routeDTO.setRoute(route);

            if (routeMap.containsKey(driver)) {
                List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
                routeListMap.add(route);
                routeMap.put(driver, routeListMap);
            } else {
                List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
                routeListMap.add(route);
                routeMap.put(driver, routeListMap);
            }
        }

        for (Driver driver : routeMap.keySet()) {
            RouteDTO ret = new RouteDTO();
            List<RouteFromEntityDTO> routesList = routeMap.get(driver);
            ret.setRouteList(routesList);
            routeListRetDTO.add(ret);
        }
        return routeListDTO;
    }

    public DriverDTO convertToSimpleDriverDTO(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setFName(driver.getFName());
        driverDTO.setLName(driver.getLName());
        driverDTO.setIDDriver(driver.getIDDriver());
        return driverDTO;
    }

    public VehicleDTO convertToSimpleVehicleDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setIDVehicle(vehicle.getIDVehicle());
        vehicleDTO.setCombustion(vehicle.getCombustion());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setBrand(vehicle.getBrand());
        vehicleDTO.setRegNum(vehicle.getRegNum());
        vehicleDTO.setGpsDeviceDTO(convertToDeviceDTO(vehicle.getGpsDevice()));
        return vehicleDTO;
    }

    public VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setIDVehicle(vehicle.getIDVehicle());
        vehicleDTO.setIDSystem(vehicle.getIDSystem());
        vehicleDTO.setCombustion(vehicle.getCombustion());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setBrand(vehicle.getBrand());
        vehicleDTO.setRegNum(vehicle.getRegNum());
        vehicleDTO.setGpsDeviceDTO(convertToDeviceDTO(vehicle.getGpsDevice()));
        vehicleDTO.setBuyDate(vehicle.getBuyDate());
        vehicleDTO.setOCDateStart(vehicle.getOCDateStart());
        vehicleDTO.setOCDateEnd(vehicle.getOCDateEnd());
        vehicleDTO.setCheckStart(vehicle.getCheckStart());
        vehicleDTO.setCheckEnd(vehicle.getCheckEnd());
        return vehicleDTO;
    }

    public RouteDTO convertAndPackRouteAndVehicle(Driver driver, Vehicle vehicle) {
        DriverDTO driverDTO = convertToSimpleDriverDTO(driver);
        VehicleDTO vehicleDTO = convertToSimpleVehicleDTO(vehicle);
        return new RouteDTO(driverDTO, vehicleDTO);
    }

    public GpsDeviceDTO convertToDeviceDTO(GpsDevice gpsDevice) {
        GpsDeviceDTO gpsDeviceDTO = new GpsDeviceDTO();

        if (gpsDevice != null) {
            gpsDeviceDTO.setIDDevice(gpsDevice.getIDDevice());
            gpsDeviceDTO.setImei(gpsDevice.getImei());
        }

        return gpsDeviceDTO;
    }

    public Vehicle convertToVehicleEntity(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setIDVehicle(vehicleDTO.getIDVehicle());
        vehicle.setIDSystem(vehicleDTO.getIDSystem());
        vehicle.setCombustion(vehicleDTO.getCombustion());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setBrand(vehicleDTO.getBrand());
        vehicle.setRegNum(vehicleDTO.getRegNum());
        vehicle.setGpsDevice(convertToDeviceEntity(vehicleDTO.getGpsDeviceDTO()));
        vehicle.setBuyDate(vehicleDTO.getBuyDate());
        vehicle.setOCDateStart(vehicleDTO.getOCDateStart());
        vehicle.setOCDateEnd(vehicleDTO.getOCDateEnd());
        vehicle.setCheckStart(vehicleDTO.getCheckStart());
        vehicle.setCheckEnd(vehicleDTO.getCheckEnd());
        return vehicle;
    }

    public GpsDevice convertToDeviceEntity(GpsDeviceDTO gpsDeviceDTO) {
        GpsDevice gpsDevice = new GpsDevice();

        if (gpsDeviceDTO != null) {
            gpsDevice.setIDDevice(gpsDeviceDTO.getIDDevice());
            gpsDevice.setImei(gpsDeviceDTO.getImei());
        }

        return gpsDevice;
    }

    public Device convertToDevice(GpsDevice gpsDevice) {
        Device device = new Device();

        if (gpsDevice != null && StringUtils.isNotBlank(gpsDevice.getImei())) {
            device.setId(Long.valueOf(gpsDevice.getImei()));
        }
        return device;
    }

}