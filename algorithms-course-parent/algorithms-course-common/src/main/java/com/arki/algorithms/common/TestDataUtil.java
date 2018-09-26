package com.arki.algorithms.common;

import java.io.*;

public class TestDataUtil {
    /**
     * Read integer array from file. The integers are separated by comma.
     * @param file
     * @return
     */
    public static Integer[] readIntegerArrayFromFile(File file){
        Logger.info("Read data from [{}]", FileUtil.getCanonicalPath(file));
        Integer[] integers = null;
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            try {
                FileReader reader = new FileReader(file);
                int read = 0;
                while(read!=-1){
                    read = reader.read();
                    if(read!=-1) sb.append((char)read);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] split = sb.toString().split(",");
            integers = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                integers[i] = Integer.valueOf(split[i]);
            }
        }
        return integers;
    }

    /**
     * Generate random integers for test.
     * @param count The amount of integers.
     * @param min The min value.
     * @param max The max value.
     * @return The file contains integers for test, separated by commas, named by "RandomInt_[{min},{max}]x{count}.txt"
     */
    public static File generateRandomIntegers(int count, int min, int max, File file) {
        file = FileUtil.createFileAccessIfExists(file);
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < count - 1; i++) {
                writer.write(MathUtil.randomInt(min, max) + ",");
            }
            writer.write(MathUtil.randomInt(min, max) + "");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
