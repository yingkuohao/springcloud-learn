package com.alicp.logapp.log.jvm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:26
 * CopyRight: taobao
 * Descrption:
 */

public class JsonUtils {

    // fastjson 的序列化配置
    public final static SerializeConfig fastjson_serializeConfig_noYear = new SerializeConfig();
    public final static SerializeConfig fastjson_serializeConfig_time = new SerializeConfig();
    public final static SerializeConfig fastjson_free_datetime = new SerializeConfig();

    // 默认打出所有属性(即使属性值为null)|属性排序输出,为了配合历史记录
    private final static SerializerFeature[] fastJsonFeatures = {SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteEnumUsingToString, SerializerFeature.SortField};

    static {
        // fastjson_serializeConfig_noYear.put(Date.class, new SimpleDateFormatSerializer("MM-dd HH:mm:ss"));
    }

    public static <T> T parseObject(String item, Class<T> clazz) {
        if (StringUtils.isBlank(item)) {
            return null;
        }
        return JSON.parseObject(item, clazz);
    }

    public static final <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return JSON.parseArray(text, clazz);
    }

    public static String toJsonString(Object object) {
        return toJsonString(object, fastjson_serializeConfig_noYear);
    }

    public static String toJsonString(Object object, SerializeConfig serializeConfig) {
        if (null == object) {
            return "";
        }
        return JSON.toJSONString(object, serializeConfig, fastJsonFeatures);
    }

}

