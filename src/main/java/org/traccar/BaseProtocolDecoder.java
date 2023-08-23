/*
 * Copyright 2012 - 2016 Anton Tananaev (anton@traccar.org)
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.traccar.model.Device;
import org.traccar.model.Position;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseProtocolDecoder extends ExtendedObjectDecoder {

    private static final Logger logger = LogManager.getLogger(BaseProtocolDecoder.class);
    
    private final Protocol protocol;

    public long addUnknownDevice(String uniqueId) {
    	logger.debug("addUnknownDevice Automatically registered dev "+uniqueId);
        Device device = new Device();
        device.setName(uniqueId);
        device.setUniqueId(uniqueId);
        try{
        device.setId(Long.valueOf(uniqueId));
        logger.debug("setId "+device.getId());
//        device.setCategory(Context.getConfig().getString("database.registerUnknown.defaultCategory"));
//
//        long defaultGroupId = Context.getConfig().getLong("database.registerUnknown.defaultGroupId");
//        if (defaultGroupId != 0) {
//            device.setGroupId(defaultGroupId);
//        }
    	        logger.debug("before addDevice "+uniqueId);
    	    	Context.getIdentityManager().addDevice(device);	
    	    }catch(Exception e){
    	    	logger.debug("adddevice exception "+e.getMessage());
    	    	return 0;
    	    }
            logger.debug("after addDevice "+uniqueId +" device.id "+device.getId());

  /*          if (defaultGroupId != 0) {
                Context.getPermissionsManager().refreshPermissions();
                if (Context.getGeofenceManager() != null) {
                    Context.getGeofenceManager().refresh();
                }
            }*/
            logger.debug("Automatically registered dev "+device.getId());
            return device.getId();
    }

    public String getProtocolName() {
        return protocol.getName();
    }

    private DeviceSession channelDeviceSession; // connection-based protocols
    private Map<SocketAddress, DeviceSession> addressDeviceSessions = new HashMap<>(); // connectionless protocols

    private long findDeviceId(SocketAddress remoteAddress, String... uniqueIds) {
    	logger.debug(this.getClass().getName()+" finddevice uniqueIds");
    	if(uniqueIds==null){
    		logger.debug(this.getClass().getName()+" finddevice uniqueIds is null");	
    	}else{
    		logger.debug(this.getClass().getName()+" finddevice uniqueIds length:"+uniqueIds.length);
    	}
    
        long deviceId = 0;
        if (uniqueIds.length > 0) {
            try {
                for (String uniqueId : uniqueIds) {
                	logger.debug(this.getClass().getName()+" loop finddevice uniqueIds:"+uniqueId);                	
                    if (uniqueId != null) {
                    	logger.debug(this.getClass().getName()+" getDeviceByUniqueId start");
                        Device device = Context.getIdentityManager().getDeviceByUniqueId(uniqueId);
                        logger.debug(this.getClass().getName()+" getDeviceByUniqueId end");
                        if (device != null) {
                        	logger.debug(this.getClass().getName()+" device!=null");
                            deviceId = device.getId();
                            logger.debug(this.getClass().getName()+" deviceID:"+deviceId);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                    logger.debug("exception findDevice:"+e.getMessage());
            }
            if (deviceId == 0) {
            	logger.debug(this.getClass().getName()+" registerUnknown");
//                if (Context.getConfig().getBoolean("database.registerUnknown")) {
            	 if (true) {
                	logger.debug(this.getClass().getName()+" registerUnknown");
                    return addUnknownDevice(uniqueIds[0]);
                }

                StringBuilder message = new StringBuilder("Unknown device -");
                for (String uniqueId : uniqueIds) {
                    message.append(" ").append(uniqueId);
                }
                if (remoteAddress != null) {
                    message.append(" (").append(((InetSocketAddress) remoteAddress).getHostString()).append(")");
                }
                //Log.warning(message.toString());
            }
        }
        return deviceId;
    }

    public DeviceSession getDeviceSession(Channel channel, SocketAddress remoteAddress, String... uniqueIds) {
    	logger.debug(this.getClass().getName()+ "start");
    	boolean ignoreSessionCache = true;
//        if (Context.getConfig().getBoolean("decoder.ignoreSessionCache")) {
    	//for debug only
    	if (channel instanceof DatagramChannel) {
    		logger.debug(this.getClass().getName()+" getDeviceSession is DatagramChannel");
    	}
    	for(String id:uniqueIds){
    		logger.debug(this.getClass().getName()+"getDeviceSession uniqueIds:"+id);
    	}
    	//end debug
    	if (ignoreSessionCache) {
        	logger.debug(this.getClass().getName()+" getDeviceSession 1");
            long deviceId = findDeviceId(remoteAddress, uniqueIds);
            if (deviceId != 0) {
            	logger.debug(this.getClass().getName()+" getDeviceSession:"+deviceId);
//            	if (Context.getConnectionManager() != null) {
//                    Context.getConnectionManager().addActiveDevice(deviceId, protocol, channel, remoteAddress);
//                }
            	logger.debug(this.getClass().getName()+" new DeviceSession:"+deviceId);
                return new DeviceSession(deviceId);
            } else {
            	logger.debug(this.getClass().getName()+" getDeviceSession return null");
                return null;
            }
        }
        if (channel instanceof DatagramChannel) {
            long deviceId = findDeviceId(remoteAddress, uniqueIds);
            DeviceSession deviceSession = addressDeviceSessions.get(remoteAddress);
            if (deviceSession != null && (deviceSession.getDeviceId() == deviceId || uniqueIds.length == 0)) {
                return deviceSession;
            } else if (deviceId != 0) {
                deviceSession = new DeviceSession(deviceId);
                addressDeviceSessions.put(remoteAddress, deviceSession);
                // TODO zarzadzanie aktywnymu urzadzeniami
                /*if (Context.getConnectionManager() != null) {
                    Context.getConnectionManager().addActiveDevice(deviceId, protocol, channel, remoteAddress);
                }*/
                return deviceSession;
            } else {
                return null;
            }
        } else {
        	logger.debug(this.getClass().getName()+" getDeviceSession is not DatagramChannel");
            if (channelDeviceSession == null) {
            	logger.debug(this.getClass().getName()+" getDeviceSession channelDeviceSession == null");
                long deviceId = findDeviceId(remoteAddress, uniqueIds);
                logger.debug(this.getClass().getName()+" getDeviceSession deviceId = "+deviceId);
                if (deviceId != 0) {
                    channelDeviceSession = new DeviceSession(deviceId);
                  /*  if (Context.getConnectionManager() != null) {
                        Context.getConnectionManager().addActiveDevice(deviceId, protocol, channel, remoteAddress);
                    }*/
                }
            }
            logger.debug(this.getClass().getName()+" getDeviceSession channelDeviceSession != null return");
            logger.debug(this.getClass().getName()+" getDeviceSession channelDeviceSession != null "+channelDeviceSession.getDeviceId());
            return channelDeviceSession;
        }
    }

    public BaseProtocolDecoder(Protocol protocol) {
        this.protocol = protocol;
    }

    public void getLastLocation(Position position, Date deviceTime) {
    	logger.debug(getClass().getName()+ "getLastLocation:"+position.getAltitude());
        if (position.getDeviceId() != 0) {
            position.setOutdated(true);

            Position last = Context.getIdentityManager().getLastPosition(position.getDeviceId());
            if (last != null) {
                position.setFixTime(last.getFixTime());
                position.setValid(last.getValid());
                position.setLatitude(last.getLatitude());
                position.setLongitude(last.getLongitude());
                position.setAltitude(last.getAltitude());
                position.setSpeed(last.getSpeed());
                position.setCourse(last.getCourse());
                position.setAccuracy(last.getAccuracy());
                logger.debug(getClass().getName()+ "getLastLocation 3:"+position.getAltitude());
            } else {
                position.setFixTime(new Date(0));
            }

            if (deviceTime != null) {
                position.setDeviceTime(deviceTime);
            } else {
                position.setDeviceTime(new Date());
            }
        }
    }

    @Override
    protected void onMessageEvent(
            Channel channel, SocketAddress remoteAddress, Object originalMessage, Object decodedMessage) {
    	logger.debug(getClass().getName()+ "onMessageEvent");
    	if(originalMessage!=null){
    		logger.debug(getClass().getName()+ "onMessageEvent originalMessage:"+originalMessage.toString());	
    	}
    	if(decodedMessage!=null){
    		logger.debug(getClass().getName()+ "onMessageEvent decodedMessage:"+decodedMessage.toString());
    	}
        Position position = null;
        if (decodedMessage != null) {
            if (decodedMessage instanceof Position) {
                position = (Position) decodedMessage;
                logger.debug(getClass().getName()+ "onMessageEvent position:"+position.getAltitude());
            } else if (decodedMessage instanceof Collection) {
                Collection positions = (Collection) decodedMessage;
                if (!positions.isEmpty()) {
                    position = (Position) positions.iterator().next();
                }
            }
        }
        if (position != null) {
        	//Log.info("onMessageEvent:updateDevice 1");
        	logger.debug(getClass().getName()+ "onMessageEvent:updateDevice 1:"+position.getAltitude());
//            Context.getConnectionManager().updateDevice(
//                    position.getDeviceId(), Device.STATUS_ONLINE, new Date());
        } else {
        	logger.debug(getClass().getName()+ "onMessageEvent:updateDevice 2");
            DeviceSession deviceSession = getDeviceSession(channel, remoteAddress);
            if (deviceSession != null) {
//            	Log.info("onMessageEvent:updateDevice 2");
            	logger.debug(getClass().getName()+ "onMessageEvent:updateDevice 3");
//                Context.getConnectionManager().updateDevice(
//                        deviceSession.getDeviceId(), Device.STATUS_ONLINE, new Date());
            }
        }
    }

    @Override
    protected Object handleEmptyMessage(Channel channel, SocketAddress remoteAddress, Object msg) {
    	logger.debug(getClass().getName()+ "handleEmptyMessage");
        DeviceSession deviceSession = getDeviceSession(channel, remoteAddress);
        //if (Context.getConfig().getBoolean("database.saveEmpty") && deviceSession != null) {
        boolean saveEmpty = true;
        if (saveEmpty && deviceSession != null) {
            Position position = new Position();
            position.setProtocol(getProtocolName());
            position.setDeviceId(deviceSession.getDeviceId());
            getLastLocation(position, null);
            logger.debug(getClass().getName()+ "handleEmptyMessage getLastPosition "+position.getLatitude() +" protocol "+position.getProtocol());
            return position;
        } else {
            return null;
        }
    }

}
