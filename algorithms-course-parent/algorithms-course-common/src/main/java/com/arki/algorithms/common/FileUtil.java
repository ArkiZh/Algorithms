package com.arki.algorithms.common;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileUtil {

    /**
     * file.separator
     * File separator ("/" on UNIX)
     */
    public static final String fileSeparator = System.getProperty("file.separator");
    /**
     * path.separator
     * Path separator (":" on UNIX)
     */
    public static final String pathSeparator = System.getProperty("path.separator");
    /**
     * line.separator
     * Line separator ("\n" on UNIX)
     */
    public static final String lineSeparator = System.getProperty("line.separator");

    /**
     * Options for the method createFile.
     */
    private enum CreateFileOption{
        OnlyNew,AccessIfExists,OverwriteIfExists,RenameIfExists;

    }

    /**
     *
     * @param file
     * @param option
     * @return <code>File</code> if the file was successfully created
     *
     * @throws IOException
     */
    private static File createFile(File file,CreateFileOption option) throws IOException {
        if(fileExists(file)){
            switch (option) {
                case OnlyNew:
                    Logger.info("File already exists, stop creating it. File name: [{}]", file.getCanonicalPath());
                    return null;
                case AccessIfExists:
                    Logger.info("File already exists, access it. File name: [{}]", file.getCanonicalPath());
                    return file;
                case OverwriteIfExists:
                    file.delete();
                    file.createNewFile();
                    Logger.info("Overwrite file. File name: [{}]", file.getCanonicalPath());
                    return file;
                case RenameIfExists:
                    return createFile(renamedFile(file), option);
            }
        }else if(file.getParentFile()!=null && !directoryExists(file.getParentFile())){
            file.getParentFile().mkdirs();
            Logger.info("Directory created. Directory name: [{}]",file.getParentFile().getCanonicalPath());
        }
        file.createNewFile();
        Logger.info("File created. File name: [{}]",file.getCanonicalPath());
        return file;
    }

    /**
     * Get the new file name similar to the old name.
     * For example:
     * temp.txt => temp(1).txt
     * temp(3).txt => temp(4).txt
     *
     * @param file
     * @return The new renamed file.
     */
    private static File renamedFile(File file){
        String cPath = file.getAbsolutePath();
        try {
            cPath = file.getCanonicalPath();
        } catch (IOException e) {
            Logger.error(null, e);
        }
        int i = cPath.lastIndexOf(fileSeparator);
        String parentPath = cPath.substring(0, i);
        String fileName = cPath.substring(i);
        int j = fileName.lastIndexOf(".");
        String suffix = j == -1 ? "" : fileName.substring(j);
        String pureName = fileName.substring(0, j);
        if(Pattern.matches(".*\\(\\d+\\)$", pureName)){
            String num = pureName.substring(pureName.lastIndexOf("(") + 1, pureName.length() - 1);
            int preNum = Integer.parseInt(num);
            pureName = pureName.substring(0, pureName.lastIndexOf("(")) + "(" + (preNum + 1) + ")";
        }else{
            pureName += "(1)";
        }
        return new File(parentPath + pureName + suffix);
    }


    /**
     * @param path
     * @return  <code>File</code> if the named file does not exist and was successfully created;
     *          <code>null</code> if the named file already exists or if IOException, SecurityException occur.
     */
    public static File createFile(String path) {
        File file = new File(path);
        return createFile(file);
    }

    /**
     * @param file
     * @return  <code>File</code> if the file does not exist and was successfully created;
     *          <code>null</code> if the file already exists or if IOException, SecurityException occur.
     */
    public static File createFile(File file) {
        try {
            return createFile(file, CreateFileOption.OnlyNew);
        } catch (IOException | SecurityException e) {
            Logger.error(null,e);
            return null;
        }
    }

    /**
     * @param path
     * @return  <code>File</code>  The created file, if execute successfully;
     *          The file will be renamed if it already exists, for example:
     *          temp.txt => temp(1).txt
     *          temp(3).txt => temp(4).txt
     */
    public static File createFileRenameIfExists(String path){
        File file = new File(path);
        return createFileRenameIfExists(file);
    }

    /**
     * @param file
     * @return  <code>File</code>  The created file, if execute successfully;
     *          The file will be renamed if it already exists, for example:
     *          temp.txt => temp(1).txt
     *          temp(3).txt => temp(4).txt
     */
    public static File createFileRenameIfExists(File file) {
        try {
            return createFile(file, CreateFileOption.RenameIfExists);
        } catch (IOException e) {
            Logger.error(null,e);
            return null;
        }
    }

    /**
     * @param path
     * @return  <code>File</code>  The created file, if it does not exist;
     *          The old file if it already exists.
     */
    public static File createFileAccessIfExists(String path){
        File file = new File(path);
        return createFileAccessIfExists(file);
    }

    /**
     * @param file
     * @return  <code>File</code>  The created file, if it does not exist;
     *          The old file if it already exists.
     */
    public static File createFileAccessIfExists(File file) {
        try {
            return createFile(file, CreateFileOption.AccessIfExists);
        } catch (IOException e) {
            Logger.error(null,e);
            return null;
        }
    }

    /**
     * @param path
     * @return  <code>File</code>  The created file, if execute successfully;
     *          If the file already exists, this method will overwrite the old file.
     */
    public static File createFileOverwriteIfExists(String path){
        File file = new File(path);
        return createFileOverwriteIfExists(file);
    }

    /**
     * @param file
     * @return  <code>File</code>  The created file, if execute successfully;
     *          If the file already exists, this method will overwrite the old file.
     */
    public static File createFileOverwriteIfExists(File file) {
        try {
            return createFile(file, CreateFileOption.OverwriteIfExists);
        } catch (IOException e) {
            Logger.error(null,e);
            return null;
        }
    }

    /**
     * Judge whether the file exists and whether it is a directory.
     * @param path
     * @return <code>true</code> if the file exists and is a directory.
     *         Otherwise, return <code>false</code>.
     */
    public static boolean directoryExists(String path){
        return directoryExists(new File(path));
    }

    /**
     * Judge whether the file exists and whether it is a directory.
     * @param file
     * @return <code>true</code> if the file exists and is a directory.
     *         Otherwise, return <code>false</code>.
     */
    public static boolean directoryExists(File file) {
        return file.exists() && file.isDirectory();
    }

    /**
     * Judge whether the file exists and whether it is a file.
     * @param path
     * @return <code>true</code> if the file exists and is a file.
     *         Otherwise, return <code>false</code>.
     */
    public static boolean fileExists(String path){
        return fileExists(new File(path));
    }

    /**
     * Judge whether the file exists and whether it is a file.
     * @param file
     * @return <code>true</code> if the file exists and is a file.
     *         Otherwise, return <code>false</code>.
     */
    public static boolean fileExists(File file) {
        return file.exists() && file.isFile();
    }


    public static void main(String[] args) throws IOException {
        File file = new File("tempFile" + fileSeparator + "temp");
        Logger.info("Is directory? [{}]",file.isDirectory());
        Logger.info("Is file? [{}]",file.isFile());
        Logger.info("Parent: " + file.getParent());
        Logger.info("Parent absolute path: " + file.getParentFile().getAbsolutePath());
        Logger.info("Parent absolute path: " + file.getParentFile().exists());

        Logger.info("Path: " + file.getPath());
        Logger.info("Absolute path: " + file.getAbsolutePath());
        Logger.info("Canonical path: " + file.getCanonicalPath());
    }

}
