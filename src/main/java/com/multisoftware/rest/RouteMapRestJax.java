package com.multisoftware.rest;

import com.multisoftware.model.Geopoint;
import com.multisoftware.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Component
@Path("jaxrest")
public class RouteMapRestJax {

    private static final Logger logger = LogManager.getLogger(RouteMapRestJax.class);

    @Autowired
    private RouteService routeService;

    @GET
    @Path("{id}")
    public String generateFeature(@PathParam("id") String id) {
        List<Geopoint> geopoints = routeService.getGeopointsForRoute(Long.valueOf(id));

        try {
            JSONObject featureCollection = new JSONObject();
            featureCollection.put("type", "FeatureCollection");

            JSONArray features = new JSONArray();

            for (Geopoint geopoint : geopoints) {

                if (geopoint == null || geopoint.getLat() == 0 || geopoint.getLon() == 0) {
                    continue;
                }

                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");

                JSONObject properties = new JSONObject();
                properties.put("Name", geopoint.getVehicle().getRegNum());
                properties.put("status", "OFF");
                properties.put("id", geopoint.getIDGeopoints());
                feature.put("properties", properties);

                JSONArray JSONArrayCoord = new JSONArray();
                JSONArrayCoord.put(geopoint.getLon());
                JSONArrayCoord.put(geopoint.getLat());
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
