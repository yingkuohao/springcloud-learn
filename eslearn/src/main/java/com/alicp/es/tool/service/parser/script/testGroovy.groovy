package com.alicp.es.tool.service.parser.script

import com.alibaba.fastjson.JSONObject
import com.google.common.collect.ImmutableMap

/**
 * Created by IntelliJ IDEA.
 * User: chengjing 
 * Date: 17/3/31
 * Time: 下午1:41
 * CopyRight: taobao
 * Descrption:         /Users/chengjing/Project/selfLearn/master/springcloud-learn/eslearn/src/main/java/com/alicp/es/tool/service/parser/script/TestGroovy.groovy
 */

class TestGroovy {
    public Object parse(String line) {
       Map map= ImmutableMap.of("a","1","b",2,"c",line);
        return JSONObject.toJSON(map);
    }

}
