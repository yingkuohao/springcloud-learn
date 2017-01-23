package com.alicp.logapp.log.jvm.msg;

import com.alicp.logapp.log.jvm.util.JmonitorConstants;
import com.alicp.logapp.log.jvm.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:25
 * CopyRight: taobao
 * Descrption:
 */

public abstract class BaseMessage {

    protected static Log LOG = LogFactory.getLog(BaseMessage.class);

    private final static int INIT_SEQ = 1;
    private final static int MAX_SEQ = 2000000000;
    private static AtomicInteger sequenceSeed = new AtomicInteger(INIT_SEQ);

    private int agentSequence = -1;

    public void setAgentSequence(int agentSequence) {
        this.agentSequence = agentSequence;
    }

    private int nextSequence() {
        int seq = sequenceSeed.incrementAndGet();

        while (seq >= MAX_SEQ) {
            sequenceSeed.compareAndSet(INIT_SEQ, seq);
            seq = sequenceSeed.incrementAndGet();
        }
        return seq;
    }

    public abstract String getType();

    /**
     * 发送和接收的消息组装方式不一样<br>
     * request:主动发起消息<br>
     * reponse:用于请求响应
     *
     * @return
     */
    public abstract boolean isRequestFormat();

    public abstract Object getBody();

    public final String buildMsg() {
        if (isRequestFormat()) {
            return buildRequestMsg();
        } else {
            return buildResponseMsg();
        }
    }

    public final byte[] buildMsgByte() {
        byte[] result = null;
        try {
            result = buildMsg().getBytes(JmonitorConstants.charset);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    private final String buildResponseMsg() {
        Map<?, ?>[] msg = new Map[2];
        Map<String, Object> head = new HashMap<String, Object>();
        Map<String, Object> body = new HashMap<String, Object>();
        // 返回给agent的的请求响应时需要用agent发过来的sequence
        if (agentSequence != -1) {
            head.put(JmonitorConstants.MSG_S, agentSequence);
        } else {
            head.put(JmonitorConstants.MSG_S, nextSequence());
        }
        head.put(JmonitorConstants.MSG_T, getType());
        body.put(JmonitorConstants.MSG_TS, 0);
        body.put(JmonitorConstants.MSG_VAL, getBody());
        msg[0] = head;
        msg[1] = body;
        return JsonUtils.toJsonString(msg);
    }

    private final String buildRequestMsg() {
        Object[] msg = new Object[2];
        Map<String, Object> head = new HashMap<String, Object>();
        head.put(JmonitorConstants.MSG_S, nextSequence());
        head.put(JmonitorConstants.MSG_T, getType());
        msg[0] = head;
        msg[1] = getBody();
        return JsonUtils.toJsonString(msg);
    }

}

