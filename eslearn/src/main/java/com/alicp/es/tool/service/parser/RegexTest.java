package com.alicp.es.tool.service.parser;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/28
 * Time: 下午4:26
 * CopyRight: taobao
 * Descrption:
 */

public class RegexTest {
    public static void main(String[] args) {


        RegexTest regexUtil = new RegexTest();
//        regexUtil.test3();
        regexUtil.test5("code");
//        regexUtil.test4();
//        regexUtil.noCaptureGroup();
//        regexUtil.positvieLookaheadGroup();
//        regexUtil.negtiveLookaheadGroup();

    }

    public void test1() {
        Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);

        Matcher matcher = pattern.matcher("<ahref=\"index.html\">主页</a>");

        String string = matcher.replaceAll("");

        System.out.println(string);
    }

    public void test2() {
        Pattern pattern = Pattern.compile("href=\"(.+?)\"");

        Matcher matcher = pattern.matcher("<ahref=\"index.html\">主页</a>");

        if (matcher.find()) {

            System.out.println(matcher.group(1));
        }
    }

    public void test3() {
//         Pattern pattern = Pattern.compile("<(.*?)>", Pattern.DOTALL);
        //.*\(<code>\w*</code>\)
//        Pattern pattern = Pattern.compile("<code>\\w*</code>", Pattern.DOTALL);
//        Pattern pattern = Pattern.compile("(<code>\\w*</code>)");
        Pattern pattern = Pattern.compile("(<code>\\w+</code>)(.*?)(<token>\\w+</token>)");

        Matcher matcher = pattern.matcher("test<code>haha</code>adfadsf<token>rty</token>12123123<code>123</code>ttt");

//         String string = matcher.replaceAll("");
        if (matcher.find()) {
                 for (int i = 0; i <= matcher.groupCount(); i++) {
                     System.out.println("group " + i + ":" + matcher.group(i));
                 }
             }
    }

    public void test5(String key) {
//         Pattern pattern = Pattern.compile("<(.*?)>", Pattern.DOTALL);
        //.*\(<code>\w*</code>\)
//        Pattern pattern = Pattern.compile("<code>\\w*</code>", Pattern.DOTALL);
//        Pattern pattern = Pattern.compile("(<code>\\w*</code>)");
        Pattern pattern = Pattern.compile("(<"+key+">\\w+</"+key+">)(.*?)(<token>\\w+</token>)");

        Matcher matcher = pattern.matcher("test<code>haha</code>adfadsf<token>rty</token>12123123<code>123</code>ttt");

//         String string = matcher.replaceAll("");
        if (matcher.find()) {
                 for (int i = 0; i <= matcher.groupCount(); i++) {
                     System.out.println("group " + i + ":" + matcher.group(i));
                 }
             }
    }

    public static void noCaptureGroup() {
        System.out.println(    "-------noCaptureGroup------");
        Pattern pattern = Pattern.compile("(?:(\\d+))?\\s?([a-zA-Z]+)?.+");
        String source = "2133 fdsdee4333";
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher.group(i));
            }
        }
    }

    //正前向查找分组（Positive lookahead）
    public static void positvieLookaheadGroup(){
        System.out.println(    "-------positvieLookaheadGroup------");
            Pattern pattern = Pattern.compile("(\\d+)\\s+(?=s)(\\w+)");
            String source = "543543   streets";        //"543543   ttreets" 匹配失败
            Matcher matcher = pattern.matcher(source);
            if(matcher.matches()){
                for(int i=0;i<=matcher.groupCount();i++){
                    System.out.println("group "+i+":"+matcher.group(i));
                }
            }
        }

//    负前向查找分组（Negative lookahead）
    public static void negtiveLookaheadGroup(){
        System.out.println(    "-------negtiveLookaheadGroup------");
            Pattern pattern = Pattern.compile("(\\d+)\\s+(?!s)(\\w+)");
            String source = "543543   ttreets";  //如"543543   streets" 匹配失败
            Matcher matcher = pattern.matcher(source);
            if(matcher.matches()){
                for(int i=0;i<=matcher.groupCount();i++){
                    System.out.println("group "+i+":"+matcher.group(i));
                }
            }
        }

    public static           void test4() {
        Pattern p =Pattern.compile("<(.*)>.*</\\1>.*<(?!\\1)");
        String line="test<code>haha</code>adfadsf<token>123</token>12123123<code>123</code>ttt";
        Matcher m =p.matcher(line);
         HashSet<String> setstr = new HashSet<String>();
         while( m.find()) {
             if (!setstr.contains(m.group(1)) && !setstr.isEmpty()) {
                 System.out.println("");
             }
         }
    }

}
