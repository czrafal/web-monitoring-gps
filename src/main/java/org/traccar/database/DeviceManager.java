/*
 * Copyright 2016 - 2017 Anton Tananaev (anton@traccar.org)
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
package org.traccar.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.multisoftware.model.GpsDevice;
import com.multisoftware.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.traccar.model.Command;
import org.traccar.model.CommandType;
import org.traccar.model.Device;
import org.traccar.model.Position;

@Service
public class DeviceManager implements IdentityManager {
    
    private static final Logger logger = LogManager.getLogger(DeviceManager.class);
    public static final long DEFAULT_REFRESH_DELAY = 300;
    
    private final Map<Long, Position> positions = new ConcurrentHashMap<>();

	@Autowired
	private DriverListService driverListService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private RouteManagerService routeManagerService;

	@Autowired
	private EntityConverterService entityConverterService;

	@Autowired
    private LocatorService locatorService;

    public DeviceManager() {

    }

    @Override
    public Device getDeviceById(long imei) {
        GpsDevice gpsDevice = locatorService.getDeviceByImei(String.valueOf(imei));
		return entityConverterService.convertToDevice(gpsDevice);
    }
    
    @Override
    public Device getDeviceByUniqueId(String uniqueId) {
    	return getDeviceById(Long.valueOf(uniqueId));
    }

    public long addUnknownDevice(String uniqueId) {
    	logger.debug("addUnknownDevice Automatically registered dev "+uniqueId);
        Device device = new Device();
/*      device.setName(uniqueId);
        device.setUniqueId(uniqueId);
        cacheDrivers.getDevicesByUniqueId().put(uniqueId, device);
        cacheDrivers.getDevicesById().put(Long.valueOf(uniqueId),device);*/
        logger.debug("Automatically registered dev "+device.getId());
        return device.getId();
    }
    
    public void addDevice(Device device){
		try {
			logger.debug("adddevice start device.id "+device.getId());
			
//			Map<Long, Device> deviceMap = cacheDrivers.getDevicesById();
//			logger.debug("adddevice 2 ");
//			Map<String, Device> deviceUniqMap = cacheDrivers.getDevicesByUniqueId();
//			logger.debug("adddevice 3 "+device.getId());
//			deviceMap.put(device.getId(), device);
//			logger.debug("adddevice 4 "+device.getId());
//			deviceUniqMap.put(device.getUniqueId(), device);
//			logger.debug("adddevice 5 "+device.getId());
//			cacheDrivers.setDevicesById(deviceMap);
//			logger.debug("adddevice 6 "+device.getId());
//			cacheDrivers.setDevicesByUniqueId(deviceUniqMap);
			
			logger.debug("adddevice end id "+device.getId());
			
		} catch (Exception e) {
			logger.debug("Exception adddevice2" + e.getMessage());
		}
	}

    public boolean isLatestPosition(Position position) {
        Position lastPosition = getLastPosition(position.getDeviceId());
        return lastPosition == null || position.getFixTime().compareTo(lastPosition.getFixTime()) >= 0;
    }
    
    @Override
    public Position getLastPosition(long deviceId) {
        return positions.get(deviceId);
    }

    public void sendCommand(Command command) throws Exception {

    }

    public Collection<CommandType> getCommandTypes(long deviceId, boolean textChannel) {
        List<CommandType> result = new ArrayList<>();
//        Position lastPosition = Context.getDeviceManager().getLastPosition(deviceId);
//        if (lastPosition != null) {
//            BaseProtocol protocol = Context.getServerManager().getProtocol(lastPosition.getProtocol());
//            Collection<String> commands;
//            commands = textChannel ? protocol.getSupportedTextCommands() : protocol.getSupportedDataCommands();
//            for (String commandKey : commands) {
//                result.add(new CommandType(commandKey));
//            }
//        } else {
//            result.add(new CommandType(Command.TYPE_CUSTOM));
//        }
        return result;
    }
    
    public void savePosition(Position position) {
    	logger.debug("Started savePosition");
    	routeManagerService.savePosition(position);
    }

	@Override
	public void savePositions(List<Position> positions) {
		positions.forEach(item->savePosition(item));
	}
    
}
