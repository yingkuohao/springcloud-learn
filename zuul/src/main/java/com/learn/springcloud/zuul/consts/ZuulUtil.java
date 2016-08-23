package com.learn.springcloud.zuul.consts;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午8:42
 * CopyRight: taobao
 * Descrption:
 */

public class ZuulUtil {

    static {
        init();
    }


    static void init() {
        ZuulConsts.blackUrlList.add("/black/*");
        ZuulConsts.whiteUrlList.add("/white/*");

        ZuulConsts.blackIPList.add("127.0.0.1");

    }


    /**
     * checks if the path matches the uri()
     *
     * @param path usually the RequestURI()
     * @return true if the pattern matches
     */
    public static   boolean checkPath(String path) {
        if (ZuulConsts.blackUrlList.contains(path)) {
            return false;
        } else if (ZuulConsts.whiteUrlList.contains(path)) {
            return true;
        }
        return true;
    }

    static  boolean checkIP(String IP) {
        if (ZuulConsts.blackIPList.contains(IP)) {
            return false;
        }
        return true;
    }


    public static boolean format(String path, String patternStr) {
        boolean tag = true;
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher mat = pattern.matcher(path);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

}
