package com.multisoftware.converter;

import com.multisoftware.model.GpsDevice;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Component
@FacesConverter("locatorConverter")
public class LocatorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null) {
            return null;
        }
        return value;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof GpsDevice) {
            GpsDevice gpsDevice = (GpsDevice)value;

            if (value != null) {
                return gpsDevice.getImei();
            }

        }
        return null;
    }
}
