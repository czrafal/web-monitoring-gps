package com.multisoftware.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class GpsDeviceDTO implements Serializable {

    private Long IDDevice;
    private String imei;

    public GpsDeviceDTO() {

    }

    public Long getIDDevice() {
        return IDDevice;
    }

    public void setIDDevice(Long IDDevice) {
        this.IDDevice = IDDevice;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GpsDeviceDTO that = (GpsDeviceDTO) o;

        return new EqualsBuilder()
                .append(IDDevice, that.IDDevice)
                .append(imei, that.imei)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(IDDevice)
                .append(imei)
                .toHashCode();
    }
}
