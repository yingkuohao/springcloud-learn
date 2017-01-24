package com.alicp.logapp.log.endpoint.customer;

import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午3:02
 * CopyRight: taobao
 * Descrption:
 */

public class MemStatus {

    public MemStatus(Date date, Map<String, Object> status) {
        this.date = date;
        this.status = status;
    }

    private Date date;
    private Map<String, Object> status;

    public Date getDate() {
        return date;
    }

    public Map<String, Object> getStatus() {
        return status;
    }
}
