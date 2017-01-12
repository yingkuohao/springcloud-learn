package com.taobao.tail.samples.websocket.echo;

import com.taobao.tail.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class EchoLogLsHandler extends TextWebSocketHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EchoService echoService;


    @Autowired
    private LogService logService;

    @Autowired
    public EchoLogLsHandler(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String logBaseDir = "/Users/chengjing/alicpaccount";
        String logs = logService.getLogs(logBaseDir);

        logger.info("logs dir=" + logs);

        String echoMessage = this.echoService.getMessage(message.getPayload());
//        session.sendMessage(new TextMessage(echoMessage));
        session.sendMessage(new TextMessage(logs));
    }


}
