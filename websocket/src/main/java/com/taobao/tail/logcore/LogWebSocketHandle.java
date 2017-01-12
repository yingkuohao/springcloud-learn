package com.taobao.tail.logcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

@ServerEndpoint("/logdetail")
public class LogWebSocketHandle {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            logger.info("log onopen" + session.getId());
            System.out.println("logopen");
            // 执行tail -f命令
//			process = Runtime.getRuntime().exec("tail -f /var/log/syslog");
            process = Runtime.getRuntime().exec("tail -f /Users/chengjing/alicpaccount/logs/alicp-account-cuntao.log");
            inputStream = process.getInputStream();

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMsg(Session session, String msg) {
        logger.info("log onMsg=" + msg);
        System.out.println("" + session.getUserProperties());
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            logger.info("logls onclose");
            if (inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}