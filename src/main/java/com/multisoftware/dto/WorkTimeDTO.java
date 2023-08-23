package com.multisoftware.dto;

public class WorkTimeDTO extends RouteDTO{

    private Double allDistanceSum;
    private Double fuelEnd;

    public WorkTimeDTO(){

    }

    public Double getAllDistanceSum() {
        return allDistanceSum;
    }

    public void setAllDistanceSum(Double allDistanceSum) {
        this.allDistanceSum = allDistanceSum;
    }

    public Double getFuelEnd() {
        return fuelEnd;
    }

    public void setFuelEnd(Double fuelEnd) {
        this.fuelEnd = fuelEnd;
    }

}
