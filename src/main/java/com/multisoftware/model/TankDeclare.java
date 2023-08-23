package com.multisoftware.model;
import java.util.Date;

import javax.persistence.*;

@SequenceGenerator(name="tankdeclare_seq1",sequenceName = "tankdeclare_seq",allocationSize=1)
@Entity
@Table(name="tanksdeclare", schema = "public")
@NamedQueries({
        @NamedQuery(name="TankDeclare.findAll", query="SELECT t FROM TankDeclare t"),
})

public class TankDeclare {

    public static String GET_TANK_BY_DAY = "TankDeclare.findByDate";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="tankdeclare_seq1")
    @Column(name="idtanksdeclare")
    private Long idtanksdeclare;

    @Column(name="idtank")
    private Long idtank;

    @Column(name="IDSystem")
    private Long IDSystem;

    @Column(name="IDVehicle")
    private Long IDVehicle;

    @Column(name="litresdeclare")
    private Double litresdeclare;

    @Column(name="adddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adddate;

    @Column(name="notificationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationdate;

    @Column(name="priceall")
    private double priceall;

    @ManyToOne
    @JoinColumn(name="idtank", referencedColumnName="idtank", insertable=false, updatable=false)
    private Tank tank;

    @ManyToOne
    @JoinColumn(name="IDVehicle", referencedColumnName="IDVehicle", insertable=false, updatable=false)
    private Vehicle vehicle1;

    @ManyToOne
    @JoinColumn(name="IDVehicle", referencedColumnName="IDVehicle", insertable=false, updatable=false)
    private Vehicle vehicle2;

    public TankDeclare() {
    }

    public Long getIDSystem() {
        return this.IDSystem;
    }

    public void setIDSystem(Long IDSystem) {
        this.IDSystem = IDSystem;
    }

    public Long getIDVehicle() {
        return this.IDVehicle;
    }

    public void setIDVehicle(Long IDVehicle) {
        this.IDVehicle = IDVehicle;
    }

    public Vehicle getVehicle1() {
        return this.vehicle1;
    }

    public void setVehicle1(Vehicle vehicle1) {
        this.vehicle1 = vehicle1;
    }

    public Vehicle getVehicle2() {
        return this.vehicle2;
    }

    public void setVehicle2(Vehicle vehicle2) {
        this.vehicle2 = vehicle2;
    }

    public Long getIdtanksdeclare() {
        return idtanksdeclare;
    }

    public void setIdtanksdeclare(Long idtanksdeclare) {
        this.idtanksdeclare = idtanksdeclare;
    }

    public Long getIdtank() {
        return idtank;
    }

    public void setIdtank(Long idtank) {
        this.idtank = idtank;
    }

    public Double getLitresdeclare() {
        return litresdeclare;
    }

    public void setLitresdeclare(Double litresdeclare) {
        this.litresdeclare = litresdeclare;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public Date getNotificationdate() {
        return notificationdate;
    }

    public void setNotificationdate(Date notificationdate) {
        this.notificationdate = notificationdate;
    }

    public double getPriceall() {
        return priceall;
    }

    public void setPriceall(double priceall) {
        this.priceall = priceall;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

}