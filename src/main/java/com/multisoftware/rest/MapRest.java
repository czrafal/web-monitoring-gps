package com.multisoftware.rest;

import java.util.List;

import com.multisoftware.dto.DriverInfo;
import com.multisoftware.model.Geopoint;
import com.multisoftware.service.VehicleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapRest {

	private static final Logger logger = LogManager.getLogger(MapRest.class);

	@Autowired
	private VehicleService activeDriversList;

	@GetMapping("/realMap")
	private String realMap() {
		List<DriverInfo> activeDrivers = activeDriversList.allDriverRealShow();

		try {
			JSONObject featureCollection = new JSONObject();
			featureCollection.put("type", "FeatureCollection");

			JSONArray features = new JSONArray();
			
			for (DriverInfo driver : activeDrivers) {
				Geopoint geopoint = driver.getGeopoint();
				if (geopoint == null || geopoint.getLat() == 0 || geopoint.getLon() == 0) {
					continue;
				}
				
				JSONObject feature = new JSONObject();
				feature.put("type", "Feature");
				
				JSONObject properties = new JSONObject();
				properties.put("Name", driver.getVehicle().getRegNum());
				properties.put("status", "OFF");
				feature.put("properties", properties);
				
				JSONArray JSONArrayCoord = new JSONArray();
				JSONArrayCoord.put(driver.getGeopoint().getLon());
				JSONArrayCoord.put(driver.getGeopoint().getLat());
				JSONObject geometry = new JSONObject();
				geometry.put("type", "Point");
				geometry.put("coordinates", JSONArrayCoord);
				feature.put("geometry", geometry);
				features.put(feature);
				featureCollection.put("features", features);
			}
			return featureCollection.toString();
		} catch (JSONException e1) {
			logger.error(e1.getMessage(), e1);
		}
		return null;
	}

}
