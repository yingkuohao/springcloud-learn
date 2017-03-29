package com.alicp.es.tool.service.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/29
 * Time: 上午11:18
 * CopyRight: taobao
 * Descrption:
 */

public class RegexUtil {


    //最后的pattern类似这种格式: Pattern.compile("(<code>\\w+</code>)(.*?)(<token>\\w+</token>)");
    public void test5(String input, Set<String> keySets) {
        Map<String, String> resultMap = new HashMap<String, String>();
        StringBuffer stringBuffer = new StringBuffer();
        keySets.forEach(n -> {
            stringBuffer.append("(<").append(n).append("\\w+</").append(n).append(">)(.*?)");
        });

        Pattern pattern = Pattern.compile(stringBuffer.toString());

        input = "test<code>haha</code>adfadsf<token>rty</token>12123123<code>123</code>ttt";
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                System.out.println("group " + i + ":" + group);
                keySets.forEach(n -> {
                    if (group.contains(n)) {
                        //如果分组中包括key,则把value放入map
                        resultMap.put(n,group);
                    }

                });
            }
        }
    }
}
