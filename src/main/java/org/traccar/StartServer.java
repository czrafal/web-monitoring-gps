package org.traccar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartServer {

    private static final Logger logger = LogManager.getLogger(StartServer.class);

    @Autowired
    private ServerManager server;

    public void startServer() {
        try {
//			new NettyServer().startServer();
            server.start();
            logger.info("Teltonika Protocol server started!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void stopNetty() {
//		nettyServer.stopServer();
    }

}
