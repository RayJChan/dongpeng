package com.dpmall.common;


import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {

    /**
     * 一般日志log
     */
    public final static Logger log=Logger.getLogger(LogUtil.class);
    
    public static void logDebug(String s){
        log.debug(s);
    } 
    public static void logInfo(String s){
        log.info(s);
    } 
    public static void logError(String s){
        log.error(s);
    }
    public static void logError(String s,Exception e){
        log.error(s,e);
    }
    
    
    /**
     * controller层日志（controller）
     */
    public final static Logger ctr=Logger.getLogger("ctrlLog");

    public static void controllerLogDebug(String s){
    	ctr.debug(s);
    } 
    public static void controllerLogInfo(String s){
    	ctr.info(s);
    } 
    public static void controllerLogError(String s){
    	ctr.error(s);
    } 
    public static void controllerLogError(String s,Exception e){
    	ctr.error(s,e);
    } 
    
    /**
     * 业务逻辑层日志（service）
     */
    public final static Logger service=Logger.getLogger("serviceLog");

    public static void serviceLogDebug(String s){
        service.debug(s);
    } 
    public static void serviceLogInfo(String s){
        service.info(s);
    } 
    public static void serviceLogError(String s){
        service.error(s);
    } 
    public static void serviceLogError(String s,Exception e){
        service.error(s,e);
    } 
    
    
    /**
     * 错误信息
     * @param logger
     * @param s
     * @param e
     */
    public static void error(Logger logger,String s,Exception e){
        logger.error(s,e);
    } 
    /** 
     * 获取异常的堆栈信息 
     *  
     * @param t 
     * @return 
     */  
    public static void getStackTrace(Throwable t){  
        StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);  
        try{  
            t.printStackTrace(pw);  
            log.error(sw.toString());  
        }finally{  
            pw.close();  
        }  
    }
    
}
