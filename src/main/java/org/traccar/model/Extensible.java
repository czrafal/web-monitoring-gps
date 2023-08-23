package org.traccar.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Extensible {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Map<String, Object> attributes = new LinkedHashMap<>();

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void set(String key, boolean value) {
        attributes.put(key, value);
    }

    public void set(String key, int value) {
        attributes.put(key, value);
    }

    public void set(String key, long value) {
        attributes.put(key, value);
    }

    public void set(String key, double value) {
        attributes.put(key, value);
    }

    public void set(String key, String value) {
        if (value != null && !value.isEmpty()) {
            attributes.put(key, value);
        }
    }

    public void add(Map.Entry<String, Object> entry) {
        if (entry != null && entry.getValue() != null) {
            attributes.put(entry.getKey(), entry.getValue());
        }
    }

    public String getString(String key) {
        if (attributes.containsKey(key)) {
            return String.valueOf(attributes.get(key));
        } else {
            return null;
        }
    }

    public double getDouble(String key) {
        if (attributes.containsKey(key)) {
            return ((Number) attributes.get(key)).doubleValue();
        } else {
            return 0.0;
        }
    }

    public boolean getBoolean(String key) {
        if (attributes.containsKey(key)) {
            return Boolean.parseBoolean(attributes.get(key).toString());
        } else {
            return false;
        }
    }

    public int getInteger(String key) {
        if (attributes.containsKey(key)) {
            return ((Number) attributes.get(key)).intValue();
        } else {
            return 0;
        }
    }

    public long getLong(String key) {
        if (attributes.containsKey(key)) {
            return ((Number) attributes.get(key)).longValue();
        } else {
            return 0;
        }
    }

    public boolean getBooleanFromLong(String key) {
        if (attributes.containsKey(key)) {
            return Integer.parseInt(attributes.get(key).toString()) != 0;
        } else {
            return false;
        }
    }

    public VehicleStatusEnum getVehicleStatus() {
        if (attributes.containsKey(Position.DIGITAL_INPUT_1)) {
            String ignitionValue = getString(Position.DIGITAL_INPUT_1);
            return VehicleStatusEnum.get(ignitionValue);
        } else {
            //return VehicleStatusEnum.NONE; for testing
            //return generateVehicleStatusForTesting();
            return VehicleStatusEnum.IGNITION_ON;
        }
    }

    public String getVehicleStatusAsString() {
        return getVehicleStatus().name();
    }
}
