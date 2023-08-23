package com.multisoftware.dto;
import java.util.List;

import com.multisoftware.model.Route;
import com.multisoftware.model.Tank;
import com.multisoftware.model.Vehicle;

public class TankDTO {

    private Vehicle vehicle;
    private Route route;
    private Tank tank;
    private List<Tank> tankList;

    public TankDTO() {}

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Tank> getTankList() {
        return tankList;
    }

    public void setTankList(List<Tank> tankList) {
        this.tankList = tankList;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

}
