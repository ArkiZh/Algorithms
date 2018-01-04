package com.arki.algorithms.common;

public class ArrayUtil {
    public static <T> String transferArrayToString(T[] a){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i].toString()).append(',');
        }
        String s = sb.substring(0, sb.length() - 1);
        return s + "]";
    }

}
