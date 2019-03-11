package com.dongpeng.common.utils;

import com.google.common.collect.Maps;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 中文拼音工具类
 */
public class PinyinUtils {
    private static final Logger logger = LoggerFactory.getLogger(PinyinUtils.class);
    private static HanyuPinyinOutputFormat format;
    private static Properties props = new Properties();
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();


    public static enum Type {
        /** 全部大写 **/
        UPPERCASE,
        /** 全部小写 **/
        LOWERCASE,
        /** 首字母大写 **/
        FIRSTUPPER
    }

    static{
        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//定义音调输出格式,这里设置为没有音调

        InputStream is = null;
        try {
            Resource resource = resourceLoader.getResource("duoyinzi.properties");
            is = resource.getInputStream();
            props.load(new InputStreamReader(is,"GBK"));
        } catch (IOException ex) {
            logger.info("Could not load properties from path, " + ex.getMessage());
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
     * 如： 明天 转换成 MINGTIAN
     * @param str：要转化的汉字
     * @param spera：转化结果的分割符
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYin(String str, String spera, Type type) {
        if(str == null || str.trim().length()==0)
            return "";
        if(type == Type.UPPERCASE){
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else{
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }


        StringBuilder py = new StringBuilder();
        String temp = "";
        String[] t;
        for(int i=0;i<str.length();i++){//逐个字进行拼音转换
            char c = str.charAt(i);
            if((int)c <= 128)//非中文不做转换
                py.append(c);
            else{
                try {
                    t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if(t == null)
                        py.append(c);//转换失败时，原字返回
                    else{
                        if(t.length>1){
                            //多音字，从多音字库中查找匹配
                            String leftString=(i==0?null:str.charAt(i-1) +""+ c);//向左拿一个字跟c拼成词组
                            String rightString=(i+1==str.length()?null:c+""+str.charAt(i+1));//向右拿一个字跟c拼成词组
                            boolean isFound=false;
                            loop :for (String dpy:t) {
                                String dycz=props.getProperty(dpy.toLowerCase());//获取该拼音对应的多音字词组
                                if(StringPlusUtils.isNotBlank(dycz)){
                                    if( (StringPlusUtils.isNotBlank(leftString) && StringPlusUtils.contains(dycz,leftString ))
                                            || (StringPlusUtils.isNotBlank(rightString) && StringPlusUtils.contains(dycz, rightString))){
                                        temp=dpy;
                                        isFound=true;
                                        break loop;
                                    }
                                }
                            }
                            if(!isFound){
                                temp=t[0];
                            }

                        }else {
                            temp = t[0];
                        }

                        if(type == Type.FIRSTUPPER) {//首字母大写
                            temp = temp.toUpperCase().charAt(0) + temp.substring(1);
                        }
                        py.append(temp+(i==str.length()-1?"":spera));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    logger.error("字符不能转换拼音", badHanyuPinyinOutputFormatCombination);
                }
            }
        }
        return py.toString();
    }

    /**
     * 获取全大写拼音
     * @param chinese 中文字符串
     * @return
     */
    public static String getPinyinToUpperCase(String chinese){
        return toPinYin(chinese, "", Type.UPPERCASE);
    }

    /**
     * 获取全小写拼音
     * @param chinese 中文字符串
     * @return
     */
    public static String getPinyinToLowerCase(String chinese){
        return toPinYin(chinese, "", Type.LOWERCASE);
    }

    /**
     * 获取首字母大写拼音
     * @param chinese 中文字符串
     * @return
     */
    public static String getPinyinToFirstUpper(String chinese){
        return toPinYin(chinese, "", Type.FIRSTUPPER);
    }

    public static void main(String[] args){
        String pinyi=PinyinUtils.toPinYin("重庆ddddd1111深林银行行长", "", Type.FIRSTUPPER);
        logger.info(pinyi);
    }
}
