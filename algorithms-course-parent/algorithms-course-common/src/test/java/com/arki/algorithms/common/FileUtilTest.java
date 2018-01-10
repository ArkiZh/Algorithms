package com.arki.algorithms.common;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileUtilTest {

    @Test
    public void testCreateFile(){
        File file = FileUtil.createFileOnlyNew("temp/temp.txt");
        FileUtil.deleteFile(file);
        FileUtil.deleteFile(new File("temp"));
    }

    @Test
    public void testCreateFileRenameIfExists(){
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            File file = FileUtil.createFileRenameIfExists("temp/temp(1)(a)(2)_.txt");
            files.add(file);
        }
        files.add(new File("temp"));
        for(File file: files){
            FileUtil.deleteFile(file);
        }
    }

    @Test
    public void testCreateFileOverwriteIfExists(){
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            files.add(FileUtil.createFileOverwriteIfExists("temp/temp.txt"));
        }
        files.add(new File("temp"));
        for(File file: files){
            FileUtil.deleteFile(file);
        }
    }

}
