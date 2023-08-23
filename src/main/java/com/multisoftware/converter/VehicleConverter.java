package com.multisoftware.converter;

import com.multisoftware.model.Vehicle;
import com.multisoftware.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="vehicleConverter")
public class VehicleConverter implements Converter {

    @Autowired
    private VehicleService vehicleService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return vehicleService.getVehicleByRegNum(2l, Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        Vehicle veh = (Vehicle)value;
        String regAsString = "";

        if (value != null) {
            regAsString = veh.getIDVehicle().toString();
        }

        return regAsString;
    }
}
