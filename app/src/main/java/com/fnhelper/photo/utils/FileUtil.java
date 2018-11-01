package com.fnhelper.photo.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.fnhelper.photo.MyApplication;
import com.fnhelper.photo.R;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class FileUtil {
    //判断SD卡是否存在
    public boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 单纯判断文件或目录是否存在返回true false
     */
    public boolean isFileExist(File file) {
        boolean hasFile = false;
        try {
            hasFile = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasFile;
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public void createFile(File file) throws IOException {
        file.createNewFile();
    }

    /**
     * 验证文件目录是否存在，不存在则进行创建操作
     *
     * @param dirPath 目录路径
     */
    public void createDir(File dirPath) {
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 得到根目录、根地址（不能在相册中检索出来）
     */
    public String getHostRootPath() {
        String sdcardPath;
        //判断SD卡是否存在
        if (hasSdcard()) {
            //path : /mnt/sdcard/Android/data/com.ybveg.govregulation/
            sdcardPath = Environment.getExternalStorageDirectory().getPath() + File.separator
                    + "Android/data/" + MyApplication.packageName + File.separator;
        } else {
            //path : data/data/com.ybveg.govregulation/
            sdcardPath = Environment.getDataDirectory().getPath() + File.separator + Environment.getDataDirectory().getPath() + MyApplication.packageName + File.separator;
        }
        return sdcardPath;
    }

    /**
     * sd卡目录 （能在相册检索出来）
     *
     * @return
     */
    public String getSDCardRootPath() {
        String sdcardPath;
        //判断SD卡是否存在
        if (hasSdcard()) {
            //path : /mnt/sdcard/
            sdcardPath = Environment.getExternalStorageDirectory().getPath() + File.separator;
        } else {
            //path : data/data/ + MSysApplication.getMPackageName()/
            sdcardPath = "/data/data/" + MyApplication.packageName + File.separator;
        }
        return sdcardPath;
    }

    //删除文件安全方式(防止进程还持有文件对象)
    public void deleteFile(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件.
     *
     * @param file
     * @return
     */
    private boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    /**
     * uri转绝对路径 (适用于本地图片)
     *(网络uri) 直接 .toString
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = MyApplication.getContext().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * File转uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = MyApplication.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return MyApplication.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    //文件后缀名
    public String getFileSuffixName(File file) {
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }



}
