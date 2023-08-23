package com.multisoftware.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name="GpsDevices.getAllDevices", query="SELECT v FROM GpsDevice v")
})
@SequenceGenerator(name="gpsdevices_seq1",sequenceName = "gpsdevices_seq",allocationSize=1)
@Entity
@Table(name="gpsdevices", schema = "public")
public class GpsDevice implements Serializable {

    public static String FIND_ALL_DEVICES = "GpsDevices.getAllDevices";
    private static final long serialVersionUID = 1L;

    public GpsDevice() {

    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="gpsdevices_seq1")
    @Column(name="iddevice")
    private Long IDDevice;

    @Column(name="imei")
    private String imei;

    public Long getIDDevice() {
        return IDDevice;
    }

    public void setIDDevice(Long iDDevice) {
        IDDevice = iDDevice;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}
