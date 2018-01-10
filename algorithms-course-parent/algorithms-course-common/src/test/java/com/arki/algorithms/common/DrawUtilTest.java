package com.arki.algorithms.common;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class DrawUtilTest {

    @Test
    public void testDrawHistogram(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            list.add(i+10);
        }
        DrawUtil.Pen pen = DrawUtil.drawHistogram(list);
        File file = pen.save("../testDrawHistogram.png");
        if (file != null) {
            Logger.info("Histogram generated successfully. File name: [{}]",file.getAbsolutePath());
            FileUtil.deleteFile(file);
        }
    }

}
