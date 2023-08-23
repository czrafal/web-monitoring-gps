package org.traccar.model;

import java.util.HashMap;
import java.util.Map;

public enum VehicleStatusEnum {

    IGNITION_ON("1"),
    IGNITION_OFF("0"),
    NONE("");

    private static final Map<String, VehicleStatusEnum> lookup = new HashMap<>();

    static
    {
        for(VehicleStatusEnum env : VehicleStatusEnum.values())
        {
            lookup.put(env.getIgnitionStatus(), env);
        }
    }

    public static VehicleStatusEnum get(String inputStatus)
    {
        return lookup.get(inputStatus);
    }

    private final String ignitionStatus;

    VehicleStatusEnum(String ignition) {
        this.ignitionStatus = ignition;
    }

    public String getIgnitionStatus() {
        return ignitionStatus;
    }

    @Override
    public String toString() {
        return ignitionStatus;
    }
}
