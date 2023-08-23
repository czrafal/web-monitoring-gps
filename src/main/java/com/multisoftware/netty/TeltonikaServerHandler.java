package com.multisoftware.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.traccar.Context;
import org.traccar.DeviceSession;
import org.traccar.helper.BitUtil;
import org.traccar.model.CellTower;
import org.traccar.model.Position;

import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class TeltonikaServerHandler extends ChannelInboundHandlerAdapter { //extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LogManager.getLogger(TeltonikaServerHandler.class);
    
    private final ChannelRepository channelRepository;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.debug("Channel active");
        ctx.fireChannelActive();
        String remoteAddress = ctx.channel().remoteAddress().toString();

        ctx.writeAndFlush("Your remote address is " + remoteAddress + ".\r\n");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //logger.debug((String)msg);
        ByteBuf buf = (ByteBuf) msg;

        Channel channel = ctx.channel();
        SocketAddress remoteAddress = channel.remoteAddress();
        decodeTcp(channel, remoteAddress, buf);
        ctx.fireChannelRead(msg);
    }

/*    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.debug(msg);
        Channel channel = ctx.channel();
        ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes(Charset.defaultCharset()));
        SocketAddress remoteAddress = channel.remoteAddress();
        decodeTcp(channel, remoteAddress, byteBuf);

        ctx.fireChannelRead(msg);


    }*/

    private Object decodeTcp(Channel channel, SocketAddress remoteAddress, ByteBuf buf) throws Exception {
        logger.debug("TeltonikaProtocolDecoder decode tcp");
        if (buf.getUnsignedShort(0) > 0) {
           return parseIdentification(channel, remoteAddress, buf);
        } else {
            buf.skipBytes(4);
            logger.debug("TeltonikaProtocolDecoder decode tcp before parseData");
            return parseData(channel, remoteAddress, buf, 0);
        }
    }

    private DeviceSession parseIdentification(Channel channel, SocketAddress remoteAddress, ByteBuf buf) {
    logger.debug("TeltonikaProtocolDecoder parseIdentificatin");
    int length = buf.readUnsignedShort();
   // String imei = buf.toString(buf.readerIndex(), length, StandardCharsets.US_ASCII);
    //    logger.debug("TeltonikaProtocolDecoder parseIdentification received imei:"+imei);
    DeviceSession deviceSession = null;//getDeviceSession(channel, remoteAddress, imei);

        logger.debug("TeltonikaProtocolDecoder parseIdentification chanel "+channel !=null);
        if (channel != null) {
        logger.debug("TeltonikaProtocolDecoder parseIdentification channel is not null");
        ByteBuf response = Unpooled.buffer(1);
        if (deviceSession != null) {
            logger.debug("TeltonikaProtocolDecoder parseIdentification deviceSession is not null "+deviceSession.getDeviceId());
            response.writeByte(1);
            logger.debug(getClass().getName()+ "writeResponse 1");
        } else {
            logger.debug("TeltonikaProtocolDecoder parseIdentification deviceSession is null");
            response.writeByte(0);
            logger.debug(getClass().getName()+ "writeResponse 2");
        }
        logger.debug(getClass().getName()+ "writeResponse:");
        channel.write(response);
        }
        return deviceSession;
    }


    public static final int CODEC_GH3000 = 0x07;
    public static final int CODEC_FM4X00 = 0x08;
    public static final int CODEC_12 = 0x0C;

    private void decodeSerial(Position position, ByteBuf buf) {
        logger.debug("TeltonikaProtocolDecoder decodeserial");
        getLastLocation(position, null);
        position.set(Position.KEY_TYPE, buf.readUnsignedByte());
        position.set(Position.KEY_COMMAND, buf.readBytes(buf.readInt()).toString(StandardCharsets.US_ASCII));
    }

    private long readValue(ByteBuf buf, int length, boolean signed) {
        switch (length) {
            case 1:
                return signed ? buf.readByte() : buf.readUnsignedByte();
            case 2:
                return signed ? buf.readShort() : buf.readUnsignedShort();
            case 4:
                return signed ? buf.readInt() : buf.readUnsignedInt();
            default:
                return buf.readLong();
        }
    }

    private void decodeParameter(Position position, int id, ByteBuf buf, int length) {
        logger.debug("TeltonikaProtocolDecoder decodeParameter id "+id);
        switch (id) {
            case 1:
            case 2:
            case 3:
            case 4:
                position.set("di" + id, readValue(buf, length, false));
                break;
            case 9:
                position.set(Position.PREFIX_ADC + 1, readValue(buf, length, false));
                break;
            case 66:
                position.set(Position.KEY_POWER, readValue(buf, length, false) * 0.001);
                break;
            case 67:
                position.set(Position.KEY_BATTERY, readValue(buf, length, false) * 0.001);
                break;
            case 70:
                position.set(Position.KEY_DEVICE_TEMP, readValue(buf, length, true) * 0.1);
                break;
            case 72:
                position.set(Position.PREFIX_TEMP + 1, readValue(buf, length, true) * 0.1);
                break;
            case 73:
                position.set(Position.PREFIX_TEMP + 2, readValue(buf, length, true) * 0.1);
                break;
            case 74:
                position.set(Position.PREFIX_TEMP + 3, readValue(buf, length, true) * 0.1);
                break;
            case 78:
                position.set(Position.KEY_RFID, readValue(buf, length, false));
                break;
            case 182:
                position.set(Position.KEY_HDOP, readValue(buf, length, false) * 0.1);
                break;
            default:
                position.set(Position.PREFIX_IO + id, readValue(buf, length, false));
                break;
        }
    }

    private void decodeLocation(Position position, ByteBuf buf, int codec) {
        logger.debug("TeltonikaProtocolDecoder decode location");
        int globalMask = 0x0f;

        if (codec == CODEC_GH3000) {

            long time = buf.readUnsignedInt() & 0x3fffffff;
            time += 1167609600; // 2007-01-01 00:00:00

            globalMask = buf.readUnsignedByte();
            if (BitUtil.check(globalMask, 0)) {

                position.setTime(new Date(time * 1000));

                int locationMask = buf.readUnsignedByte();

                if (BitUtil.check(locationMask, 0)) {
                    position.setLatitude(buf.readFloat());
                    position.setLongitude(buf.readFloat());
                }

                if (BitUtil.check(locationMask, 1)) {
                    position.setAltitude(buf.readUnsignedShort());
                }

                if (BitUtil.check(locationMask, 2)) {
                    position.setCourse(buf.readUnsignedByte() * 360.0 / 256);
                }

                if (BitUtil.check(locationMask, 3)) {
                    position.setSpeed(buf.readUnsignedByte());
                }

                if (BitUtil.check(locationMask, 4)) {
                    int satellites = buf.readUnsignedByte();
                    position.set(Position.KEY_SATELLITES, satellites);
                    position.setValid(satellites >= 3);
                }

                if (BitUtil.check(locationMask, 5)) {
                    CellTower cellTower = CellTower.fromLacCid(buf.readUnsignedShort(), buf.readUnsignedShort());

                    if (BitUtil.check(locationMask, 6)) {
                        cellTower.setSignalStrength((int) buf.readUnsignedByte());
                    }

//                    position.setNetwork(new Network(cellTower));

                } else if (BitUtil.check(locationMask, 6)) {
                    position.set(Position.KEY_RSSI, buf.readUnsignedByte());
                }

                if (BitUtil.check(locationMask, 7)) {
                    position.set(Position.KEY_OPERATOR, buf.readUnsignedInt());
                }

            } else {
                getLastLocation(position, new Date(time * 1000));
            }

        } else {
            position.setTime(new Date(buf.readLong()));
            position.set("priority", buf.readUnsignedByte());
            position.setLongitude(buf.readInt() / 10000000.0);
            position.setLatitude(buf.readInt() / 10000000.0);
            position.setAltitude(buf.readShort());
            position.setCourse(buf.readUnsignedShort());

            int satellites = buf.readUnsignedByte();
            position.set(Position.KEY_SATELLITES, satellites);
            position.setValid(satellites != 0);
            position.setSpeed(buf.readUnsignedShort());
            position.set(Position.KEY_EVENT, buf.readUnsignedByte());
            buf.readUnsignedByte(); // total IO data records
        }

        // Read 1 byte data
        if (BitUtil.check(globalMask, 1)) {
            int cnt = buf.readUnsignedByte();
            for (int j = 0; j < cnt; j++) {
                decodeParameter(position, buf.readUnsignedByte(), buf, 1);
            }
        }

        // Read 2 byte data
        if (BitUtil.check(globalMask, 2)) {
            int cnt = buf.readUnsignedByte();
            for (int j = 0; j < cnt; j++) {
                decodeParameter(position, buf.readUnsignedByte(), buf, 2);
            }
        }

        // Read 4 byte data
        if (BitUtil.check(globalMask, 3)) {
            int cnt = buf.readUnsignedByte();
            for (int j = 0; j < cnt; j++) {
                decodeParameter(position, buf.readUnsignedByte(), buf, 4);
            }
        }

        // Read 8 byte data
        if (codec == CODEC_FM4X00) {
            int cnt = buf.readUnsignedByte();
            for (int j = 0; j < cnt; j++) {
                decodeParameter(position, buf.readUnsignedByte(), buf, 8);
            }
        }

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

    private List<Position> parseData(Channel channel, SocketAddress remoteAddress, ByteBuf buf, int locationPacketId, String... imei) {
        List<Position> positions = new LinkedList<>();
        logger.debug("TeltonikaProtocolDecoder.ParseData");
       // if (!(channel instanceof DatagramChannel)) {
        //    buf.readUnsignedInt(); // data length
      //s  }

        int codec = buf.readUnsignedByte();
        int count = buf.readUnsignedByte();
        //DeviceSession deviceSession = getDeviceSession(channel, remoteAddress, imei);
        DeviceSession deviceSession = new DeviceSession(352848022867325L);
        /*if (deviceSession == null) {
            return null;
        }
*/
        for (int i = 0; i < count; i++) {
            Position position = new Position();
            position.setProtocol(getProtocolName());
            logger.debug("loop "+getProtocolName());
            position.setDeviceId(deviceSession.getDeviceId());

            if (codec == CODEC_12) {
                decodeSerial(position, buf);
            } else {
                decodeLocation(position, buf, codec);
            }
            position.setLatitude(54.395576);
            position.setLongitude(18.147387);
            positions.add(position);
            logger.debug("parseData position deviceId:"+ deviceSession.getDeviceId()+" altitude:"+position.getAltitude() +" latitude:"+position.getLatitude() +" longitude:"+position.getLongitude() +"speed:"+position.getSpeed()+" addres:"+position.getAddress()+" devicetime:"+position.getDeviceTime().toString()+" fixTime:"+position.getFixTime().toString()+"ignition status:"+position.getVehicleStatusAsString());
        }

        if (!positions.isEmpty()) {
            Context.getIdentityManager().savePositions(positions);
        }


        if (channel != null) {
/*            if (channel instanceof DatagramChannel) {
                ByteBuf response = Unpooled.buffer(256);
                response.writeShort(5);
                response.writeShort(0);
                response.writeByte(0x01);
                response.writeByte(locationPacketId);
                response.writeByte(count);
                channel.write(response);
            } else {*/
                ByteBuf response = Unpooled.buffer(256);
                response.writeInt(count);
                channel.write(response);
           // }
        }
        logger.debug("TeltonikaProtocolDecoder.ParseData 2");
        return positions;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (logger.isDebugEnabled()) {
            logger.debug("Channel Count is " + this.channelRepository.size());
        }
    }

    private String getProtocolName(){
        return "teltonika";
    }

}
