package com.multisoftware.converter;

import com.multisoftware.model.Vehicle;
import com.multisoftware.service.DriverListService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter(value="driverAndVehicleConv")
public class DriverAndVehicleConverter implements Converter {

    @Autowired
    private DriverListService bean;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
        return bean.getVehicleByRegNum(new Long(2), Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        Vehicle veh = (Vehicle)value;
        String regAsString = "";

        if (value != null) {
            regAsString = veh.getIDVehicle().toString();
        }

        return regAsString;
    }

}