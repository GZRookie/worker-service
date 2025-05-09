package com.worker.biz.common.utils;

import java.io.File;
import java.util.List;

/**
 * 文件工具类
 *
 *  @author
 * @date: 2023-11-4 02:31
 */
public class FileUtils {

    public static void queryFile(String dirPath, List<String> filePathList) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            if (fileArr != null) {
                for (File file : fileArr) {
                    //如果是文件，加入文件路径列表
                    if (file.isFile()) {
                        filePathList.add(file.getAbsolutePath());
                        //如果是目录，递归查询
                    } else if (file.isDirectory()) {
                        queryFile(file.getAbsolutePath(), filePathList);
                    }
                }
            }
        }
    }

    public static void deleteFile(String dirPath) {
        File file = new File(dirPath);
        deleteDirectory(file);
    }

    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectory(child);
                }
            }
        }
        dir.delete();
    }
}
