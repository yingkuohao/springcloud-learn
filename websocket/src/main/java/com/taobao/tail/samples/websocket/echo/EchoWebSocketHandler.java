package com.taobao.tail.samples.websocket.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class EchoWebSocketHandler extends TextWebSocketHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EchoService echoService;


    @Autowired
    public EchoWebSocketHandler(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        Process process = Runtime.getRuntime().exec("ls /Users/chengjing/alicpaccount/logs");
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String logs = "";
        String line;
        while ((line = reader.readLine()) != null) {
            // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
            logger.info("line=" + line);
            logs += (line + "\r\n");
        }

        logger.info("logs dir=" + logs);

        String echoMessage = this.echoService.getMessage(message.getPayload());
//        session.sendMessage(new TextMessage(echoMessage));
        session.sendMessage(new TextMessage(logs));
    }

}
