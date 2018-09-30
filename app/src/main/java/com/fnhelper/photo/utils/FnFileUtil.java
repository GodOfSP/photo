package com.fnhelper.photo.utils;


import java.io.File;


public class FnFileUtil {
    public static FileUtil fileUtil = new FileUtil();
    private final static String ROOT_DIR = "fn/";
    //分支目录（分支目录）
    private final static String PHOTO_DIR = "fnPhoto/";
    private final static String APKDOWN_DIR = "fnApkDown/";

    //创建项目所有目录
    public static void createAllDirs() {
        fileUtil.createDir(new File(getPhotoFullDir()));
        fileUtil.createDir(new File(getAppDownLoadDir()));
    }

    //得到图片文件 系统相册只能检索sd卡根目录的图片，不能检索到存在项目包中的图片
    public static String getPhotoFullDir() {
        return fileUtil.getSDCardRootPath() + ROOT_DIR + PHOTO_DIR;
    }
    //得到app下载目录
    public static String getAppDownLoadDir() {
        return fileUtil.getSDCardRootPath() + ROOT_DIR + APKDOWN_DIR;
    }

    public static void createPicDirs(){
        fileUtil.createDir(new File(getPhotoFullDir()));
    }
}
