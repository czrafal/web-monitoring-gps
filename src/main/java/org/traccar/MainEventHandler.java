/*
 * Copyright 2012 - 2015 Anton Tananaev (anton@traccar.org)
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
//import org.traccar.helper.Log;
import org.traccar.model.Position;

public class MainEventHandler extends IdleStateAwareChannelHandler {

    private static final Logger logger = LogManager.getLogger(MainEventHandler.class);
            
    private final Set<String> connectionlessProtocols = new HashSet<>();

    public MainEventHandler() {
        String connectionlessProtocolList = "ON";//Context.getConfig().getString("status.ignoreOffline");
        if (connectionlessProtocolList != null) {
            connectionlessProtocols.addAll(Arrays.asList(connectionlessProtocolList.split(",")));
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    	  logger.debug("NEW MESSAGE RECEIVED:"+e.getMessage());
        if (e.getMessage() != null && e.getMessage() instanceof Position) {

            Position position = (Position) e.getMessage();
//            try {
//                Context.getDeviceManager().updateLatestPosition(position);
//            } catch (SQLException error) {
//                Log.warning(error);
//            }
            logger.debug("NEW MESSAGE RECEIVED:"+e.getMessage());
//            String uniqueId = Context.getIdentityManager().getDeviceById(position.getDeviceId()).getUniqueId();
            String uniqueId = "";
            // Log position
            StringBuilder s = new StringBuilder();
            s.append(formatChannel(e.getChannel())).append(" ");
            s.append("id: ").append(uniqueId).append(", ");
//            s.append("time: ").append(new SimpleDateFormat(Log.DATE_FORMAT).format(position.getFixTime())).append(", ");
            s.append("lat: ").append(String.format("%.5f", position.getLatitude())).append(", ");
            s.append("lon: ").append(String.format("%.5f", position.getLongitude())).append(", ");
            s.append("speed: ").append(String.format("%.1f", position.getSpeed())).append(", ");
            s.append("course: ").append(String.format("%.1f", position.getCourse()));
            Object cmdResult = position.getAttributes().get(Position.KEY_RESULT);
            if (cmdResult != null) {
                s.append(", result: ").append(cmdResult);
            }
//
//            Context.getStatisticsManager().registerMessageStored(position.getDeviceId());
        }
    }

    private static String formatChannel(Channel channel) {
        return String.format("[%08X]", channel.getId());
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
//        Log.info(formatChannel(e.getChannel()) + " connected");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
//        Log.info(formatChannel(e.getChannel()) + " disconnected");
        closeChannel(e.getChannel());

        BaseProtocolDecoder protocolDecoder = (BaseProtocolDecoder) ctx.getPipeline().get("objectDecoder");
        if (ctx.getPipeline().get("httpDecoder") == null
                && !connectionlessProtocols.contains(protocolDecoder.getProtocolName())) {
//            Context.getConnectionManager().removeActiveDevice(e.getChannel());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
//        Log.warning(formatChannel(e.getChannel()) + " error", e.getCause());
        closeChannel(e.getChannel());
    }

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) {
//        Log.info(formatChannel(e.getChannel()) + " timed out");
        closeChannel(e.getChannel());
    }

    private void closeChannel(Channel channel) {
        if (!(channel instanceof DatagramChannel)) {
            channel.close();
        }
    }

}
