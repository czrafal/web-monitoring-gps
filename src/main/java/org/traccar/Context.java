/*
 * Copyright 2015 - 2017 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar;

/*import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ning.http.client.AsyncHttpClient;*/

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.traccar.database.DeviceManager;
import org.traccar.database.IdentityManager;
/*import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.util.URIUtil;
import org.traccar.database.AliasesManager;
import org.traccar.database.CalendarManager;
import org.traccar.database.ConnectionManager;
import org.traccar.database.DataManager;
import org.traccar.database.DeviceManager;

import org.traccar.database.NotificationManager;
import org.traccar.database.PermissionsManager;
import org.traccar.database.GeofenceManager;
import org.traccar.database.StatisticsManager;
import org.traccar.geocoder.BingMapsGeocoder;
import org.traccar.geocoder.FactualGeocoder;
import org.traccar.geocoder.GeocodeFarmGeocoder;
import org.traccar.geocoder.GisgraphyGeocoder;
import org.traccar.geocoder.GoogleGeocoder;
import org.traccar.geocoder.MapQuestGeocoder;
import org.traccar.geocoder.NominatimGeocoder;
import org.traccar.geocoder.OpenCageGeocoder;
import org.traccar.geocoder.Geocoder;
import org.traccar.geolocation.UnwiredGeolocationProvider;
import org.traccar.helper.Log;
import org.traccar.geolocation.GoogleGeolocationProvider;
import org.traccar.geolocation.GeolocationProvider;
import org.traccar.geolocation.MozillaGeolocationProvider;
import org.traccar.geolocation.OpenCellIdGeolocationProvider;
import org.traccar.notification.EventForwarder;
import org.traccar.smpp.SmppClient;
import org.traccar.web.WebServer;*/

public final class Context {

    private Context() {
    }

    private static Config config;

    public static Config getConfig() {
        return config;
    }

    private static boolean loggerEnabled = true;

    public static boolean isLoggerEnabled() {
        return loggerEnabled;
    }


    private static IdentityManager identityManager;

    public static IdentityManager getIdentityManager() {
    	identityManager = new DeviceManager();
        return identityManager;
    }


   /* private static DeviceManager deviceManager;

    public static DeviceManager getDeviceManager() {
        return deviceManager;
    }

    private static ConnectionManager connectionManager;

    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    private static ServerManager serverManager;

    public static ServerManager getServerManager() {
        return serverManager;
    }


    private static SmppClient smppClient;

    public static SmppClient getSmppManager() {
        return smppClient;
    }

    public static void init(String[] arguments) throws Exception {

        config = new Config();
        if (arguments.length <= 0) {
            throw new RuntimeException("Configuration file is not provided");
        }

        config.load(arguments[0]);

        loggerEnabled = config.getBoolean("logger.enable");
        if (loggerEnabled) {
            Log.setupLogger(config);
        }

        objectMapper = new ObjectMapper();
        objectMapper.setConfig(
                objectMapper.getSerializationConfig().without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));

        if (config.hasKey("database.url")) {
            dataManager = new DataManager(config);
        }

        if (dataManager != null) {
            deviceManager = new DeviceManager(dataManager);
        }
        
        connectionManager = new ConnectionManager();

        if (config.getBoolean("event.geofenceHandler")) {
            geofenceManager = new GeofenceManager(dataManager);
            calendarManager = new CalendarManager(dataManager);
        }

        if (config.getBoolean("event.enable")) {
            notificationManager = new NotificationManager(dataManager);
            Properties velocityProperties = new Properties();
            velocityProperties.setProperty("file.resource.loader.path",
                    Context.getConfig().getString("templates.rootPath", "templates") + "/");
            velocityProperties.setProperty("runtime.log.logsystem.class",
                    "org.apache.velocity.runtime.log.NullLogChute");

            String address;
            try {
                address = config.getString("web.address", InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                address = "localhost";
            }

        }

        serverManager = new ServerManager();

        if (config.getBoolean("sms.smpp.enable")) {
            smppClient = new SmppClient();
        }

    }

    public static void init(IdentityManager testIdentityManager) {
        config = new Config();
        objectMapper = new ObjectMapper();
        identityManager = testIdentityManager;
    }
*/
}
