package com.dongpeng.common.utils;

import com.google.common.collect.Maps;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 表达式解析工具类
 */
public class MvelUtils {
    private static final Logger logger = LoggerFactory.getLogger(MvelUtils.class);

    //默认精度
    private static final int DEF_SCALE=2;

    /**
     * 解析计算预计结果为double类型的表达式，并返回精确后的值
     * @param expression 表达式
     * @param vars 参数集合
     * @return
     */
    public static Double evalDouble(String expression, Map vars){
        // compile表达式 异常表达式会报错
        Serializable serializable = null;
        BigDecimal rs = null;
        try {
            serializable=MVEL.compileExpression(expression);
            rs= new BigDecimal((Double) MVEL.executeExpression(serializable, vars)) ;
        }catch (Exception e){
            logger.error("表达式解析错误", e);
        }
       
        return  null==rs?null:rs.setScale(DEF_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 解析计算预计结果为integer类型的表达式，并返回值
     * @param expression 表达式
     * @param vars 参数集合
     * @return
     */
    public static Integer evalInteger(String expression, Map vars){
        // compile表达式 异常表达式会报错
        Serializable serializable = null;
        Integer rs = null;
        try {
            serializable=MVEL.compileExpression(expression);
            rs= (Integer) MVEL.executeExpression(serializable, vars);
        }catch (Exception e){
            logger.error("表达式解析错误", e);
        }

        return  rs;
    }

    /**
     * 解析判断预计结果为Boolean类型的表达式，并返回值
     * @param expression 表达式
     * @param vars 参数集合
     * @return
     */
    public static Boolean evalBoolean(String expression, Map vars){
        // compile表达式 异常表达式会报错
        Serializable serializable = null;
        Boolean  rs = null;
        try {
            serializable=MVEL.compileExpression(expression);
            rs= (Boolean) MVEL.executeExpression(serializable, vars);
        }catch (Exception e){
            logger.error("表达式解析错误", e);
        }

        return  rs;
    }

    /**
     * 解析预计结果为String类型的表达式，并返回值
     * @param expression 表达式
     * @param vars 参数集合
     * @return
     */
    public static String evalString(String expression, Map vars){
        // compile表达式 异常表达式会报错
        Serializable serializable = null;
        String  rs = null;
        try {
            serializable=MVEL.compileExpression(expression);
            rs= (String) MVEL.executeExpression(serializable, vars);
        }catch (Exception e){
            logger.error("表达式解析错误", e);
        }

        return  rs;
    }

    public static void main(String[] args){
        String expression="(W-300)*0.2+170+(S-4)*3*P";
        Map vars= Maps.newHashMap();
        vars.put("W",401.33 );
        vars.put("S",12);
        vars.put("P",12);
        Double rs=MvelUtils.evalDouble(expression, vars);
        System.out.println(rs);

        String expression2="p*3";
        Map vars2= Maps.newHashMap();
        vars2.put("p",12);
        Integer rs2=MvelUtils.evalInteger(expression2, vars2);
        System.out.println(rs2);

        String expression3="1==a";
        Map vars3= Maps.newHashMap();
        vars3.put("a",12);
        boolean rs3=MvelUtils.evalBoolean(expression3, vars3);
        System.out.println(rs3);

        String expression4="\"Hello World \" + 用户名";
        Map vars4= Maps.newHashMap();
        vars4.put("用户名","lancer");
        String rs4=MvelUtils.evalString(expression4, vars4);
        System.out.println(rs4);
    }

}
