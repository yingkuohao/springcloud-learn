package com.alicp.logapp.log.jvm.util;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:14
 * CopyRight: taobao
 * Descrption:
 */

public class AlimonitorJmonitorException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AlimonitorJmonitorException(String message){
        super(message);
    }

    public AlimonitorJmonitorException(String message, Throwable e){
        super(message, e);
    }

}
