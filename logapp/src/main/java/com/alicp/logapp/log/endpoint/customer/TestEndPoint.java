package com.alicp.logapp.log.endpoint.customer;

import com.alibaba.fastjson.JSONObject;
import com.alicp.logapp.log.jvm.util.IPUtils;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午3:12
 * CopyRight: taobao
 * Descrption:      http://localhost:7002/testEndPoint
 */

@Component
public class TestEndPoint implements Endpoint {
    @Override
    public String getId() {
        return "testEndPoint";
    }

    @Override
    public boolean isEnabled() {
        return true;                  //注意要打开,否则访问报404
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public Object invoke() {       //核心就是invoke方法
        Map<String, String> map = new HashMap<String, String>();
        map.put("appName", "logapp");
        map.put("ip", IPUtils.getLocalIp());
        //不用强转json,框架会处理
//        return JSONObject.toJSONString(map);
        return map;
    }
}
