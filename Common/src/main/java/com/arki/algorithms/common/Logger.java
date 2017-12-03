package com.arki.algorithms.common;

import org.slf4j.LoggerFactory;

public class Logger {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger("LoggerName");

    public static void info(String msg){
        log.info(getInvokerInfo() + "=============== " + msg);
    }

    public static void info(String msg, Object... arguments){
        log.info(getInvokerInfo() + "=============== " + msg, arguments);
    }

    /**
     * Method Description: Get the method info which invoke this Logger.java's method.
     *  For example: [com.arki.algorithms.common.Logger.info(Logger.java:10)]
     * @return String
     */
    private static String getInvokerInfo(){
        //
        StackTraceElement invoker = Thread.currentThread().getStackTrace()[3];
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(invoker.getClassName())           //[{qualified className}
                .append(".").append(invoker.getMethodName())    //.{methodName}
                .append("(").append(invoker.getFileName())     //({fillName}
                .append(":").append(invoker.getLineNumber())    //:{lineNumber}
                .append(")]");                                  //)]
        return sb.toString();
    }
}
