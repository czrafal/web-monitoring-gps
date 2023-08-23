package com.multisoftware.repository;

import com.multisoftware.dto.DriverAndVehicle;
import com.multisoftware.model.Driver;
import com.multisoftware.service.DriverListService;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriversRepositoryTest {
/*
    @Autowired
    private DriverListService driverListService;*/

    @Autowired
    private DriversRepository driversRepository;

    @Test
    public void allDriversAndVehiclesShowTest() {
        List<DriverAndVehicle> driverAndVehicle = driversRepository.allDriversAndVehiclesShow();
    }

    @Test
    public void findAllTest() {
        List<Driver> driverAndVehicle = driversRepository.findAll();
    }

    @Test
    public void getDriversBySystemIDTest() {
        driversRepository.getDriversBySystemID(2l);
    }

   /* @Test
    public void allVehiclesGet() {
        driverListService.allVehiclesGet();
    }*/
}
