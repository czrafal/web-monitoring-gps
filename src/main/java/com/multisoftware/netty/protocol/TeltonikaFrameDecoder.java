package com.multisoftware.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ChannelHandler.Sharable
public class TeltonikaFrameDecoder //extends FixedLengthFrameDecoder
{

    private static final int MESSAGE_MINIMUM_LENGTH = 12;
//
//    public TeltonikaFrameDecoder(int frameLength) {
//        super(frameLength);
//    }
//
//    @Override
//    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) {
//        if (in.readableBytes() < MESSAGE_MINIMUM_LENGTH) {
//            return null;
//        }
//
//        int length = in.getUnsignedShort(in.readerIndex());
//        if (length > 0) {
//            if (in.readableBytes() >= (length + 2)) {
//                return in.readBytes(length + 2);
//            }
//        } else {
//            int dataLength = in.getInt(in.readerIndex() + 4);
//            if (in.readableBytes() >= (dataLength + 12)) {
//                return in.readBytes(dataLength + 12);
//            }
//        }
//
//        return null;
//    }
}
