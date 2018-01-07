package com.arki.algorithms.common;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileUtilTest {

    @Test
    public void testCreateFile(){
        File file = FileUtil.createFile("temp/temp.txt");
        file.delete();
        Logger.info("File deleted. File name: [{}]",file.getAbsolutePath());
        File file1 = new File("temp");
        file1.delete();
        Logger.info("File deleted. File name: [{}]",file1.getAbsolutePath());
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
            file.delete();
            Logger.info("File deleted. File name: [{}]",file.getAbsolutePath());
        }
    }

    @Test
    public void testCreateFileOverwriteIfExists(){
        for (int i = 0; i < 2; i++) {
            FileUtil.createFileOverwriteIfExists("temp/temp.txt");
        }
        File file = new File("temp/temp.txt");
        file.delete();
        Logger.info("File deleted. File name: [{}]",file.getAbsolutePath());
        File file1 = new File("temp");
        file1.delete();
        Logger.info("File deleted. File name: [{}]",file1.getAbsolutePath());
    }

}
