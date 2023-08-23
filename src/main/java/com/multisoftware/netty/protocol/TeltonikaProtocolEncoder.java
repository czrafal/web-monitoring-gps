package com.multisoftware.netty.protocol;

import com.multisoftware.netty.User;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class TeltonikaProtocolEncoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (!(msg instanceof String) || !((String) msg).startsWith("login ")) {
            ctx.fireChannelRead(msg);
            return;
        }

        ctx.writeAndFlush("Successfully logged in as " + ". \r\n");
    }

}
