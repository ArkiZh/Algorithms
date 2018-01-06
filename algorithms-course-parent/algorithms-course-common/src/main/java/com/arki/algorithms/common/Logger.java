package com.arki.algorithms.common;

import org.slf4j.LoggerFactory;

public class Logger {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger("LoggerName");

    /**
     * Log a message at the INFO level.
     *
     * @param msg the message string to be logged
     */
    public static void info(String msg){
        log.info(getInvokerInfo() + "=============== " + msg);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     * @param format    the format string
     * @param arguments a list of arguments
     */
    public static void info(String format, Object... arguments){
        if(arguments==null||arguments.length==0) log.info(format);
        else log.info(getInvokerInfo() + "=============== " + format, arguments);
    }

    /**
     * Log a message at the ERROR level.
     *
     * @param msg the message string to be logged
     */
    public static void error(String msg){
        log.error(getInvokerInfo() + "=============== " + msg);
    }

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     * @param format    the format string
     * @param arguments a list of arguments
     */
    public static void error(String format, Object... arguments){
        if(arguments==null||arguments.length==0) log.error(format);
        else log.error(getInvokerInfo() + "=============== " + format, arguments);
    }

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception.
     *            If it is <code>null</code>, will use the default value: <code>t.getLocalizedMessage()</code>
     * @param t   the exception (throwable) to log
     */
    public static void error(String msg, Throwable t){
        if(msg==null) msg = t.getLocalizedMessage();
        log.error(getInvokerInfo() + "=============== " + msg, t);
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
