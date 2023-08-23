package com.multisoftware.netty;

import com.multisoftware.netty.protocol.TeltonikaProtocolDecoder;
import com.multisoftware.netty.protocol.TeltonikaProtocolEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.traccar.protocol.TeltonikaFrameDecoder;


import java.util.List;


@Component
@RequiredArgsConstructor
public class SimpleChatChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SimpleChatServerHandler simpleChatServerHandler;
    private final TeltonikaServerHandler teltonikaServerHandler;
    private final LoginHandler loginHandler;
    private final StringEncoder stringEncoder = new StringEncoder();
    private final StringDecoder stringDecoder = new StringDecoder();
    private final TeltonikaProtocolEncoder teltonikaProtocolEncoder;
    private final TeltonikaProtocolDecoder teltonikaProtocolDecoder;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // Add the text line codec combination first,
       // pipeline.addLast(new FixedLengthFrameDecoder(1024 * 1024));
       // pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 1024, Delimiters.lineDelimiter()));
        //pipeline.addLast(stringDecoder);
        //pipeline.addLast(new TeltonikaProtocolDecoder());
        pipeline.addLast(new TeltonikaFrameDecoder());
        pipeline.addLast(stringEncoder);
        pipeline.addLast(teltonikaServerHandler);
        pipeline.addLast(teltonikaProtocolEncoder);
        //pipeline.addLast(teltonikaProtocolDecoder(TeltonikaProtocol.this));

        // pipeline.addLast(simpleChatServerHandler);
        // pipeline.addLast(loginHandler);
    }
}