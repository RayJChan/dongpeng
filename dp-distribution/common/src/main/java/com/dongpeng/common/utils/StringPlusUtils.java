package com.dongpeng.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理加强工具类
 * @see StringUtils
 */
public class StringPlusUtils extends StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringPlusUtils.class);

    /**
     * 判断list是否为空
     * @param list
     * @return true=空  false=不为空
     */
    public static boolean isBlankList(List list){
        return null==list || list.isEmpty();
    }

    /**
     * 验证是否正确国内手机号码
     * <ul>
     * <strong>三大运营商</strong>
     * <li>中国电信号段 133、149、153、173、177、180、181、189、199</li>
     * <li>中国联通号段 130、131、132、145、155、156、166、175、176、185、186</li>
     * <li>中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</li>
     * <strong>其他号段</strong>
     * <li>14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。</li>
     * <strong>虚拟运营商</strong>
     * <li>电信：1700、1701、1702</li>
     * <li>移动：1703、1705、1706</li>
     * <li>联通：1704、1707、1708、1709、171</li>
     * <li>卫星通信：1349</li>
     * </ul>
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[0-9])|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (isBlank(phone) || phone.length() != 11) {
            logger.warn("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                logger.warn("手机号码不正确");
            }
            return isMatch;
        }
    }

    /**
     * 清除不可见的Unicode字符
     * @param source 待处理的字符串
     */
    public static String clearInvisibleUnicode(String source){
        return source.replaceAll("\\p{C}", "");
    }

    public static void main(String[] args){
        System.out.println(StringPlusUtils.isPhone("19928251365"));;
        String test="17777161709\u202C";
        for (char s:test.toCharArray()) {
            System.out.println("char: "+s);
        }
        test=clearInvisibleUnicode(test);
        for (char s:test.toCharArray()) {
            System.out.println("char: "+s);
        }
    }
}
