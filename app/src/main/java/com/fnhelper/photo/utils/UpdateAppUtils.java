package com.fnhelper.photo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.beans.UpdateBean;
import com.fnhelper.photo.interfaces.RetrofitService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ls on 2018/1/11.
 */
public class UpdateAppUtils {


    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private static int progress;
    private static ProgressBar seekBar;
    private static TextView cancel_download;
    private static TextView seekPrecent;
    private static boolean cancelUpdate = false;

    //是否更新的提示对话框
    private static TextView title_tv, note_tv, sure_tv, cancel_tv;


    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD:
                    seekBar.setProgress(progress);
                    seekPrecent.setText(progress + " %");
                    break;
                case DOWNLOAD_FINISH:
                    seekBar.setProgress(progress);
                    seekPrecent.setText(progress + " %");
                    installApk((Context) msg.obj);
                    noticeDialog.dismiss();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 检测是否需要更新
     *
     * @param context
     */
    public static void checkVersion(final Context context) {


        Call<UpdateBean> call = RetrofitService.createMyAPI().Update(getVersionName(context));
        call.enqueue(new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().isState()){
                            if (response.body().getData().isRape()) {
                                //显示对话框  --> 强制型
                                showNoticeDialogExit(context, response.body().getData().getTitle(), response.body().getData().getNote(), response.body().getData().getUrl());
                            } else {
                                //显示对话框  --> 不强制
                                showNoticeDialog(context, response.body().getData().getTitle(), response.body().getData().getNote(), response.body().getData().getUrl());
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {

            }
        });



    }

    /**
     * 获取当前的版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionCode = "";
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    private static AlertDialog noticeDialog;
    /**
     * 是否更新提示窗口  强制
     */

    private static void showNoticeDialogExit(final Context context, final String title, String note, final String urlPath) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_update_dialog, null);
        title_tv = (TextView) v.findViewById(R.id.title_tv);
        note_tv = (TextView) v.findViewById(R.id.note_tv);
        sure_tv = (TextView) v.findViewById(R.id.sure_btn);
        cancel_tv = (TextView) v.findViewById(R.id.cancel_btn);
        seekBar = (ProgressBar) v.findViewById(R.id.seekBar);
        seekPrecent = (TextView) v.findViewById(R.id.seekbar_percent);

        // builder.setView(v);
       noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
        noticeDialog.getWindow().setContentView(v);
        noticeDialog.setCancelable(false);  //不可按回退键取消
        //  noticeDialog.setContentView(v);
        title_tv.setText(title);//标题
        note_tv.setText(note);//正文
        sure_tv.setOnClickListener(new View.OnClickListener() { //更新按钮
            @Override
            public void onClick(View v) {
                title_tv.setText("正在更新");
                note_tv.setVisibility(View.GONE);
                sure_tv.setVisibility(View.GONE);
                cancel_tv.setVisibility(View.GONE);
                seekBar.setVisibility(View.VISIBLE);
                seekPrecent.setVisibility(View.VISIBLE);
                downloadApk(context, urlPath);

            }
        });

        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //暂不更新
                noticeDialog.dismiss();
                System.exit(0);
            }
        });
    }

    /**
     * 是否更新提示窗口  不强制
     */

    public static void showNoticeDialog(final Context context, final String title, String note, final String urlPath) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_update_dialog, null);
        title_tv = (TextView) v.findViewById(R.id.title_tv);
        note_tv = (TextView) v.findViewById(R.id.note_tv);
        sure_tv = (TextView) v.findViewById(R.id.sure_btn);
        cancel_tv = (TextView) v.findViewById(R.id.cancel_btn);
        seekBar = (ProgressBar) v.findViewById(R.id.seekBar);
        seekPrecent = (TextView) v.findViewById(R.id.seekbar_percent);
        noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
        noticeDialog.getWindow().setContentView(v);
        title_tv.setText(title);//标题
        note_tv.setText(note);//正文

        sure_tv.setOnClickListener(new View.OnClickListener() { //更新按钮
            @Override
            public void onClick(View v) {
                title_tv.setText("正在更新");
                note_tv.setVisibility(View.GONE);
                sure_tv.setVisibility(View.GONE);
                cancel_tv.setVisibility(View.VISIBLE);
                cancel_tv.setText("取消");
                seekBar.setVisibility(View.VISIBLE);
                seekPrecent.setVisibility(View.VISIBLE);
                downloadApk(context, urlPath);
            }
        });
        cancel_tv.setText("暂不更新");
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //暂不更新
                cancelUpdate = true;
                noticeDialog.dismiss();
            }
        });
    }


    private static void downloadApk(Context context, String urlPath) {
        new downloadApkThread(context, urlPath).start();
    }


    /**
     * 下载程序
     */
    public static class downloadApkThread extends Thread {
        Context context;
        String urlPath;

        public downloadApkThread(Context context, String urlPath) {
            this.context = context;
            this.urlPath = urlPath;
        }

        @Override
        public void run() {
            try {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {

                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator, "fnApp.apk");

                    //判断文件是否存在，如果存在就删除。
                    if (apkFile.exists()) {
                        apkFile.delete();
                    }
                    apkFile.createNewFile();

                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            Message message = new Message();
                            message.what = DOWNLOAD_FINISH;
                            message.obj = context;
                            mHandler.sendMessage(message);
                            break;
                        }

                        fos.write(buf, 0, numread);
                        fos.flush();
                    } while (!cancelUpdate);
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 安装apk
     */
    private static void installApk(Context context) {
        File apkfile = new File(getExternalSdCardPath() + File.separator, "fnApp.apk"); // 适配所有机型的手机
        if (!apkfile.exists()) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上

            Uri apkUri = FileProvider.getUriForFile(context, "com.fnhelper.photo.FileProvider", apkfile);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(install);
        } else {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");
            context.startActivity(i);
        }


    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String[] toSearch = FileUtils.readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * 获取扩展SD卡存储目录
     * <p>
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static String getExternalSdCardPath() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.i("SDCard", "挂载的.." + Environment.getExternalStorageState() + Environment.getExternalStorageDirectory().getAbsolutePath());
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getPath();
        } else {
            Log.i("SDCard", "没挂在.." + Environment.getExternalStorageState());
        }

        String path = null;

        File sdCardFile = null;

        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {
            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);

                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }

        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }

        return null;

    }
}


