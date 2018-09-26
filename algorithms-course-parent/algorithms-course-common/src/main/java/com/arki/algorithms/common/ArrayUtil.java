package com.arki.algorithms.common;

import java.lang.reflect.Array;

public class ArrayUtil {
    public static <T> String transferArrayToString(T[] a){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < a.length; i++) {
            sb.append(String.valueOf(a[i])).append(',');
        }
        String s = sb.substring(0, sb.length() - 1);
        return s + "]";
    }
    public static <T> T[] copyArray(T[] a){

        int length = a.length;
        T[] t = (T[])Array.newInstance(a[0].getClass(), length);
        for (int i = 0; i < length; i++) {
            t[i] = a[i];
        }
        return t;
    }

    public static <T> T[] copyArray(T[] a, int startIndex, int endIndex) {
        T[] t = (T[]) Array.newInstance(a[0].getClass(), endIndex - startIndex + 1);
        for (int i = startIndex; i <= endIndex; i++) {
            t[i - startIndex] = a[i];
        }
        return t;
    }

    public static void main(String[] args) {
        Integer[] integers = new Integer[10];
        for (int i = 0; i < 10; i++) {
            integers[i] = i;
        }
        Integer[] a = copyArray(integers);
    }

}
