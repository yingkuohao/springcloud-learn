package com.taobao.tail.samples.websocket.echo;

import com.taobao.tail.logcore.TailLogThread;
import com.taobao.tail.service.LogService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class TailLogHandler extends TextWebSocketHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EchoService echoService;


    @Autowired
    private LogService logService;

    @Autowired
    public TailLogHandler(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, TextMessage message) throws Exception {

        try {
            logger.info("log onopen" + session.getId());
            String payload = message.getPayload();
            if (StringUtils.isNotBlank(payload)) {
                logger.info("message={}", payload);
                //日志路径不包括log的话,直接return.
                if (!payload.contains("log")) {
                    logger.error("log path is not correct!");
                    session.sendMessage(new TextMessage("log path is not correct!"));
                } else {
//                    final String logPath = "/Users/chengjing/alicpaccount/logs/alicp-account-cuntao.log";
                    String baseDir = "";
                    final String logPath = baseDir + payload;
                    // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            String line;
                            logger.info("logtail run,logPath={}", logPath);
                            try {
                                // 执行tail -f命令
                                Process process = Runtime.getRuntime().exec("tail -f " + logPath);
                                InputStream inputStream = process.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                while ((line = reader.readLine()) != null) {
                                    // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                                    logger.info("line=" + line);
                                    session.sendMessage(new TextMessage(line + "<br>"));
                                }
                            } catch (IOException e) {
                                logger.error("tail log error,e={}", e);
                            }
                        }
                    };
                    new Thread(runnable).start();
                    //yingkhtodo:desc:线程要定时关闭,比如1分钟不操作
                }
            }


        } catch (Exception e) {
            logger.error("handle taillog error,e={}", e);
        }
    }


}
