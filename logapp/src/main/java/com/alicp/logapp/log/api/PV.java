package com.alicp.logapp.log.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午6:26
 * CopyRight: taobao
 * Descrption:
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PV {

    String key();
}
