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
        String path = getCanonicalPath(file);
        if(fileExists(file)){
            switch (option) {
                case OnlyNew:
                    Logger.info("File already exists, stop creating it. File name: [{}]", path);
                    return null;
                case AccessIfExists:
                    Logger.info("File already exists, access it. File name: [{}]", path);
                    return file;
                case OverwriteIfExists:
                    file.delete();
                    file.createNewFile();
                    Logger.info("Overwrite file. File name: [{}]", path);
                    return file;
                case RenameIfExists:
                    return createFile(renamedFile(file), option);
            }
        }else if(file.getParentFile()!=null && !directoryExists(file.getParentFile())){
            file.getParentFile().mkdirs();
            Logger.info("Directory created. Directory name: [{}]",getCanonicalPath(file.getParentFile()));
        }
        file.createNewFile();
        Logger.info("File created. File name: [{}]",path);
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
        String cPath = getCanonicalPath(file);
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
    public static File createFileOnlyNew(String path) {
        File file = new File(path);
        return createFileOnlyNew(file);
    }

    /**
     * @param file
     * @return  <code>File</code> if the file does not exist and was successfully created;
     *          <code>null</code> if the file already exists or if IOException, SecurityException occur.
     */
    public static File createFileOnlyNew(File file) {
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

    /**
     * Delete the file (directory included).
     * @param file
     * @return <code>true</code> The file exists and is deleted successfully.
     *          <code>false</code> otherwise.
     */
    public static boolean deleteFile(File file) {
        String path = getCanonicalPath(file);
        if (!file.exists()) {
            Logger.info("File not exist. Path: [{}]", path);
            return false;
        }
        String type = file.isDirectory() ? "directory" : file.isFile() ? "file" : "unknownType";
        //Logger.info("Delete {} start...   Path: [{}]", type, path);
        boolean result = false;
        if (file.isFile()) {
            result = file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
            result = file.delete();
        } else {
            Logger.info("Skip this {}. Path: [{}]", type, path);
        }
        Logger.info("Delete {}: [{}]   Path: [{}]", type, result ? "Success" : "Failed", path);
        return result;
    }

    /**
     * Delete the file (directory included).
     * @param path
     * @return <code>true</code> The file exists and is deleted successfully.
     *          <code>false</code> otherwise.
     */
    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    public static boolean directoryEmpty(File directory){
        if(directoryExists(directory)){
            String[] list = directory.list();
            return list.length == 0;
        }
        return false;
    }


    /**
     * Get the canonical path of a file.
     * @param file
     * @return the canonical path.
     *          If <code>IOException</code> occurs, the absolute path will be returned instead.
     */
    public static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            Logger.error(null, e);
            return file.getAbsolutePath();
        }
    }

    public static void main(String[] args) {
        File file = createFileOnlyNew("temp" + fileSeparator + "hello" + fileSeparator + "world.temp");
        deleteFile("temp");
    }

}
