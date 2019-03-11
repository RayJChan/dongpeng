package com.dongpeng.common.utils;

import java.math.BigDecimal;

/**
 * 精准 简单浮点型 数学运算工具类
 */
public class MathUtils {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE=2;

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add (double v1,double v2)
    {
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2=new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double subtract (double v1,double v2)
    {
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2=new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2)
    {
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2=new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后 2 位，以后的数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div (double v1,double v2)
    {
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，由scale参数指定精度 ，以后的数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static double div (double v1,double v2,int scale)
    {
        if(scale<0)
        {
            throw new IllegalArgumentException("scale必须为0或正整数");
        }

        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2=new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算地球两点间距离
     * @param lng
     * @param lat
     * @param lng2
     * @param lat2
     * @return 距离(km)
     */
    public static double distanceBetween(double lng,double lat,double lng2,double lat2){
        if(lng==0 || lat==0 || lng2==0 || lat2==0) return -1;

        double radLat1 = lat * Math.PI/180.0,
                radLat2 = lat2 * Math.PI/180.0;
        double a = radLat1 - radLat2,
                b = (lng * Math.PI/180.0) - (lng2 * Math.PI/180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b/2),2)));
        s = s * 6378.14;
        s = Math.round(s*1000)/1000;
        //	    if(s<1){
        //            return s*1000 +"m";
        //	    }else{
        //	       return Math.round(s*10)/10 +"km";
        //	    }
        return s;
    }
}
