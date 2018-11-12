package com.fnhelper.photo.mine;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.utils.ImageUtil;
import com.luck.picture.lib.permissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 我的code
 */
public class MyCodeAc extends BaseActivity {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.com_code)
    ImageView comCode;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.viewGroup)
    ConstraintLayout viewGroup;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.my_code)
    SimpleDraweeView myCode;
    @BindView(R.id.wx_logo)
    ImageView wxLogo;
    @BindView(R.id.tv3)
    TextView tv3;

    private EasyPopup mSharePop;
    private Bitmap mCodeBitmap = null;

    private static final int THUMB_SIZE = 150;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
        comTitle.setText("相册二维码");
        comRight.setImageResource(R.drawable.share_btn);
    }

    @Override
    protected void initData() {


        //请求权限
        new RxPermissions(MyCodeAc.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //获取头衔bitmap
                            Uri uri = Uri.parse(Constants.sHeadImg);
                            Bitmap bitmap = ImageUtil.returnBitmap(uri);
                            //生成二维码
                            mCodeBitmap = CodeUtils.createImage(Constants.ID, 400, 400, bitmap);
                            myCode.setImageURI(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mCodeBitmap, null, null)));
                            //初始化pop
                            initSharePop();
                        } else {
                            Toast.makeText(MyCodeAc.this, "请打开读写权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    protected void initListener() {

        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                showSharePop();
            }
        });

        /**
         * 长按保存二维码
         */
        myCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyCodeAc.this);
                builder.setItems(new String[]{"保存二维码"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mCodeBitmap!=null){
                            saveBitmap(mCodeBitmap);

                        }

                    }
                });
                builder.show();



                return false;
            }
        });
    }



    private void initSharePop() {
        mSharePop = EasyPopup.create()
                .setContentView(MyCodeAc.this, R.layout.share_pop)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView(viewGroup)
                .apply();


        RelativeLayout friend = mSharePop.findViewById(R.id.friend_rl);
        RelativeLayout cicler = mSharePop.findViewById(R.id.cicler_rl);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(true);
            }
        });

        cicler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(false);
            }
        });
    }

    private void showSharePop() {
        mSharePop.showAtAnchorView(findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);
    }

    private IWXAPI wxAPI = null;

    private void share(final boolean t) {


        //请求权限
        new RxPermissions(MyCodeAc.this).request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //初始化微信
                            if (wxAPI == null) {
                                wxAPI = WXAPIFactory.createWXAPI(MyCodeAc.this, Constants.WECHAT_APPID, true);
                            }
                            if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                showBottom(MyCodeAc.this, "没有安装微信");
                                return;
                            }
                            wxAPI.registerApp(Constants.WECHAT_APPID);


                            WXImageObject imgObj = new WXImageObject(mCodeBitmap);
                            WXMediaMessage msg = new WXMediaMessage();
                            msg.mediaObject = imgObj;

                            //设置缩略图
                            Bitmap thumbBmp = Bitmap.createScaledBitmap(mCodeBitmap, THUMB_SIZE, THUMB_SIZE, true);
                            msg.thumbData = ImageUtil.bmpToByteArray(thumbBmp, true);  // 设置所图；


                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = ImageUtil.buildTransaction("img");
                            req.message = msg;
                            req.scene = t ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                            wxAPI.sendReq(req);
                        } else {
                            Toast.makeText(MyCodeAc.this, "请打开权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCodeBitmap.recycle();
    }

    public void saveBitmap(Bitmap bitmap) {

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "蜂鸟微商相册");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        showBottom(MyCodeAc.this,"保存成功！");
    }

}
