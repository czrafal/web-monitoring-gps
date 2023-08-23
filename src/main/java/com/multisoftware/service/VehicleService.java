package com.multisoftware.service;

import com.multisoftware.dto.DriverInfo;
import com.multisoftware.model.Geopoint;
import com.multisoftware.model.Route;
import com.multisoftware.model.Vehicle;
import com.multisoftware.repository.DriversRepository;
import com.multisoftware.repository.VehiclesRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.traccar.model.Device;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Refactor of VehiclesListBean.
 *
 */

@Service
public class VehicleService {

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private DriversRepository driversRepository;

    public List<Vehicle> allVehicleShow() {
        return vehiclesRepository.allVehiclesByIDSystem(2l);
    }

    public Vehicle getVehicleByRegNum(Long idSystem, Long idVehicle) {
        return vehiclesRepository.getVehicleById(idSystem, idVehicle);
    }

    public void addNewVehilce(Vehicle newVehicle){
        newVehicle.setIDSystem(new Long(2));
        vehiclesRepository.save(newVehicle);
    }

    @SuppressWarnings("unchecked")
    public List<Vehicle> allVehiclesGet(){
        return vehiclesRepository.allVehiclesByIDSystem(2l);
    }

    public void updateVehicle(Vehicle vehicle){
        vehiclesRepository.save(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        Vehicle vehicleToRemove = vehiclesRepository.getVehicleById(2l, vehicle.getIDVehicle());
        vehiclesRepository.delete(vehicleToRemove);
    }

    public List<DriverInfo> allDriverRealShow() { // TODO check if all is needed
        List<DriverInfo> driverInfoReturnList = new ArrayList<>();
        List<Object[]> driverInfoList = driversRepository.allDriverRealShow();

        for (Object[] obj : driverInfoList) {
            DriverInfo driverObj = new DriverInfo();
            Vehicle vehicle = (Vehicle)obj[0];
            Geopoint geopoint = (Geopoint)obj[1];
            Route route = (Route)obj[2];
            driverObj.setIDDriver(BigInteger.ONE);

            if (vehicle != null) {
                driverObj.setVehicle(vehicle);
                Device device = new Device();

                if (vehicle.getGpsDevice() != null && StringUtils.isNotBlank(vehicle.getGpsDevice().getImei())) {
                    device.setId(Long.valueOf(vehicle.getGpsDevice().getImei()));
                }

                driverObj.setDevice(device);
            }

            if (geopoint != null) {
                driverObj.setGas(geopoint.getGas());
                driverObj.setMaxspeed(geopoint.getMaxspeed());
                driverObj.setGeopoint(geopoint);
            }

            if (route != null) {
                driverObj.setRoute(route);
            }

            driverInfoReturnList.add(driverObj);
        }

        return driverInfoReturnList;
    }
}
