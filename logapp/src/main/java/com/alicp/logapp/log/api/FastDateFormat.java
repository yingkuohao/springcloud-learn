package com.alicp.logapp.log.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午4:03
 * CopyRight: taobao
 * Descrption:
 */

public class FastDateFormat {
    private final SimpleDateFormat fmt = createSimpleDateFormat();

    private char[] buffer = new char[23];

    private long lastSecond = -1;

    public String format(long timestamp) {
        formatToBuffer(timestamp);
        return new String(buffer, 0, 23);
    }

    public String format(Date date) {
        return format(date.getTime());
    }

    public void formatAndAppendTo(long timestamp, StringBuilder appender) {
        formatToBuffer(timestamp);
        appender.append(buffer, 0, 23);
    }

    private void formatToBuffer(long timestamp) {
        long second = timestamp / 1000;
        if (second == lastSecond) {
            int ms = (int) (timestamp % 1000);
            buffer[22] = (char) (ms % 10 + '0');
            ms /= 10;
            buffer[21] = (char) (ms % 10 + '0');
            buffer[20] = (char) (ms / 10 + '0');
        } else {
            String result = fmt.format(new Date(timestamp));
            result.getChars(0, result.length(), buffer, 0);
        }
    }

    String formatWithoutMs(long timestamp) {
        long second = timestamp / 1000;
        if (second != lastSecond) {
            String result = fmt.format(new Date(timestamp));
            result.getChars(0, result.length(), buffer, 0);
        }
        return new String(buffer, 0, 19);
    }

    private SimpleDateFormat createSimpleDateFormat() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return fmt;
    }
}

