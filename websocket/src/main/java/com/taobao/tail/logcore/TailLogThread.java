package com.taobao.tail.logcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TailLogThread extends Thread {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

        private BufferedReader reader;
    private Session session;

    public TailLogThread(InputStream in, Session session) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.session = session;

    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                logger.info("line=" + line);
                session.getBasicRemote().sendText(line + "<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}