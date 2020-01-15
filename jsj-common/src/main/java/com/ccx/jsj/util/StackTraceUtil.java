package com.ccx.jsj.util;

import java.util.stream.Stream;

/**
 * 记录栈信息工具类
 */
public class StackTraceUtil {

    public static String getTrace(Throwable throwable){
        StringBuilder builder = new StringBuilder();
        Stream.of(throwable.getStackTrace()).forEach(
                msg-> builder.append("\t\r\n").append(msg)
        );
        return builder.toString();
    }
}
