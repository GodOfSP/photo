package com.fnhelper.photo.mine;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.interfaces.Constants;
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

import java.io.ByteArrayOutputStream;
import java.io.File;

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
                            Bitmap bitmap = returnBitmap(uri);
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
    }

    /**
     * 获取缓存中的bitmap
     *
     * @param uri
     * @return
     */
    private Bitmap returnBitmap(Uri uri) {

        Bitmap bitmap = null;
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getSmallImageFileCache().getResource(new SimpleCacheKey(uri.toString()));
        File file = resource.getFile();
        bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;

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

                            Bitmap mCodeBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.year_big);

                            WXImageObject imgObj = new WXImageObject(mCodeBitmap);
                            WXMediaMessage msg = new WXMediaMessage();
                            msg.mediaObject = imgObj;

                            //设置缩略图
                            Bitmap thumbBmp = Bitmap.createScaledBitmap(mCodeBitmap, THUMB_SIZE, THUMB_SIZE, true);
                            mCodeBitmap.recycle();
                            msg.thumbData = bmpToByteArray(thumbBmp, true);  // 设置所图；


                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("img");
                            req.message = msg;
                            req.scene = t ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                        } else {
                            Toast.makeText(MyCodeAc.this, "请打开权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 50, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCodeBitmap.recycle();
    }
}
