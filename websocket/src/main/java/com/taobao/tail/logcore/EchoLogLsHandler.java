package com.taobao.tail.logcore;

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


    @Autowired
    private LogService logService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //yingkhtodo:desc:暂时测试使用,client端没有发消息过来
        String logBaseDir = "/Users/chengjing/alicpaccount";
        String logs = logService.getLogsLocal(logBaseDir);

        logger.info("logs dir=" + logs);
        session.sendMessage(new TextMessage(logs));
    }


}
