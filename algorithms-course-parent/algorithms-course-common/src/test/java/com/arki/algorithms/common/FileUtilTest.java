package com.arki.algorithms.common;

import org.junit.Test;

import java.io.File;

public class FileUtilTest {

    @Test
    public void testCreateFile(){
        File file = FileUtil.createFile("temp/temp.arki");
    }

    @Test
    public void testCreateFileRenameIfExists(){
        for (int i = 0; i < 2; i++) {
            FileUtil.createFileRenameIfExists("temp/temp.arki");
        }
    }

    @Test
    public void testCreateFileOverwriteIfExists(){
        for (int i = 0; i < 2; i++) {
            FileUtil.createFileOverwriteIfExists("temp/temp.arki");
        }
    }

}
