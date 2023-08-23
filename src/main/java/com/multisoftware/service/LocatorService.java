package com.multisoftware.service;

import com.multisoftware.model.GpsDevice;
import com.multisoftware.repository.GpsDevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocatorService {

    @Autowired
    private GpsDevicesRepository gpsDevicesRepository;

    @Autowired
    private EntityConverterService entityConverterService;

    public void addNewDevice(GpsDevice newDevice){
        gpsDevicesRepository.save(newDevice);
    }

    @SuppressWarnings("unchecked")
    public List<GpsDevice> allDevicesGet(){
        return gpsDevicesRepository.findAll();
    }

    public void updateDevice(GpsDevice device){
        gpsDevicesRepository.save(device);
    }

    public void removeDevice(GpsDevice gpsDevice){
        GpsDevice deviceToRemove = gpsDevicesRepository.getOne(gpsDevice.getIDDevice());
        gpsDevicesRepository.delete(deviceToRemove);
    }

    public GpsDevice getDeviceByImei(String imei) {
        return gpsDevicesRepository.getGpsDeviceByImei(imei);
    }

    public GpsDevice getDeviceById(Long iddevice) {
        return gpsDevicesRepository.getGpsDeviceById(iddevice);
    }
}
