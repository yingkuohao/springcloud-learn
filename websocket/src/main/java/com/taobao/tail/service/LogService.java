package com.taobao.tail.service;

import com.taobao.tail.consts.LogConsts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/11
 * Time: 下午4:50
 * CopyRight: taobao
 * Descrption:
 */

@Component
public class LogService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getLogs(String logBaseDir) {
        String logs = "";
        InputStream inputStream = null;
        try {
//            String s = "/Users/chengjing/alicpaccount/logs";
            if (StringUtils.isBlank(logBaseDir)) {
                logger.error("log dir is blank!");
                return null;
            }
            Process process = Runtime.getRuntime().exec("ls " + logBaseDir);
            inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                logger.info("line=" + line);

                logs += (line + LogConsts.prefix);
            }
        } catch (Exception e) {
            logger.error("getLogs errror", e);
        } finally {
            try {
                logger.info("logls onclose");
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e) {

            }
        }
        return logs;
    }
}
