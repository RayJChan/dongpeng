package com.dongpeng.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * url正则匹配工具类
 */
public class UrlPatternUtils {
    private static Logger logger = LoggerFactory.getLogger(UrlPatternUtils.class);
    private  Pattern[] includePattern = null;
    private  Pattern[] excludePattern = null;

    /**
     * 构造工具类
     * @param include  include ulr 集合
     * @param exclude  exclude ulr 集合
     */
    public  UrlPatternUtils(String include,String exclude){
        if (null != include && !"".equals(include)) {
            String[] arr = include.split(",");
            includePattern = new Pattern[arr.length];
            for (int i = 0; i < arr.length; i++) {
                includePattern[i] = Pattern.compile(getRegPath(arr[i]));
            }
        }
        if (null != exclude && !"".equals(exclude)) {
            String[] arr = exclude.split(",");
            excludePattern = new Pattern[arr.length];
            for (int i = 0; i < arr.length; i++) {
                excludePattern[i] = Pattern.compile(getRegPath(arr[i]));
            }
        }
        logger.info("excludePattern: "+excludePattern);
        logger.info("includePattern: "+includePattern);
    }

    /**
     * 检验访问请求是否在include列表中
     *
     * @param requestUrl
     * @return true : 匹配成功 <br/> false : 匹配失败
     */
    public  boolean checkInclude(String requestUrl) {
        boolean flag = false;
        if (null == includePattern || includePattern.length == 0) {
            return flag;
        }
        for (Pattern pat : includePattern) {
            if (flag = pat.matcher(requestUrl).matches())
                break;
        }
        logger.info("checkInclude: "+flag+" , requestUrl: "+requestUrl);
        return flag;
    }

    /**
     * 检验访问请求是否在exclude列表中
     *
     * @param requestUrl
     * @return true : 匹配成功 <br/> false : 匹配失败
     */
    public  boolean checkExclude(String requestUrl) {
        boolean flag = false;
        if (null == excludePattern || excludePattern.length == 0) {
            return flag;
        }
        for (Pattern pat : excludePattern) {
            if (flag = pat.matcher(requestUrl).matches())
                break;
        }
        logger.info("checkExclude: "+flag+" , requestUrl: "+requestUrl);
        return flag;
    }

    /**
     * 将通配符表达式转化为正则表达式
     * @param path
     * @return
     */
    private static String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for(int i=0;i<len;i++){
            if (chars[i] == '*'){//遇到*字符
                if (preX){//如果是第二次遇到*，则将**替换成.*
                    sb.append(".*");
                    preX = false;
                }else if(i+1 == len){//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                    sb.append("[^/]*");
                }else{//否则单星后面还有字符，则不做任何动作，下一把再做动作
                    preX = true;
                    continue;
                }
            }else{//遇到非*字符
                if (preX){//如果上一把是*，则先把上一把的*对应的[^/]*添进来
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?'){//接着判断当前字符是不是?，是的话替换成.
                    sb.append('.');
                }else{//不是?的话，则就是普通字符，直接添进来
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        UrlPatternUtils urlPatternUtils=new UrlPatternUtils(null, "*/fonts/.*");
        System.out.println(urlPatternUtils.checkExclude("df/fonts/ds.ht"));
    }

}
