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
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for INFO.
     *
     * @param format    the format string
     * @param arguments a list of arguments
     */
    public static void info(String format, Object... arguments){
        if(arguments==null||arguments.length==0) log.info(format);
        else if(arguments.length==1) log.info(format, arguments[0]);
        else if (arguments.length==2) log.info(format, arguments[0], arguments[1]);
        else log.info(getInvokerInfo() + "=============== " + format, arguments);
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
