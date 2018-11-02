package com.fnhelper.photo.mine;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.ImageUtil;
import com.luck.picture.lib.permissions.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;
import static com.luck.picture.lib.tools.PictureFileUtils.isGooglePhotosUri;

/**
 * 扫描二维码
 */
public class ScanCodeAc extends BaseActivity {


    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.input)
    LinearLayout input;
    @BindView(R.id.open_light_cb)
    CheckBox openLightCb;
    @BindView(R.id.open_light_tv)
    TextView openLightTv;
    @BindView(R.id.open_light)
    LinearLayout openLight;
    @BindView(R.id.activity_second)
    FrameLayout activitySecond;


    private static final int REQUEST_IMAGE = 100;
    private static final int INPUT_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    initSaoma(getSupportFragmentManager());
                } else {
                    Toast.makeText(ScanCodeAc.this,
                            "请先打开摄像头权限", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    @Override
    public void setContentView() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {



    }

    public void initSaoma(FragmentManager manager) {
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                // 扫码成功拿到字符串截取出 bizId  跳转到店铺页面
              if (result.length()!=32){
                  showBottom(ScanCodeAc.this, "不能用该二维码进行关注操作！"); 
              }else {
                  loadingDialog.setHintText("处理中");
                  loadingDialog.show();
                  follow(result);
              }
            }

            @Override
            public void onAnalyzeFailed() {
                showBottom(ScanCodeAc.this, "未识别");
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        openLightCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /**
                     * 打开闪光灯
                     */
                    CodeUtils.isLightEnable(true);
                } else {

                    /**
                     * 关闭闪光灯
                     */
                    CodeUtils.isLightEnable(false);
                }
            }
        });

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        /**
         * 替换我们的扫描控件
         */
        manager.beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(ScanCodeAc.this,uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            // 扫码成功拿到字符串截取出 bizId  跳转到店铺页面
                            if (result.length()!=32){
                                showBottom(ScanCodeAc.this, "不能用该二维码进行关注操作！");
                            }else {
                                loadingDialog.setHintText("处理中");
                                loadingDialog.show();
                                follow(result);
                            }
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            showBottom(ScanCodeAc.this, "解析二维码失败！");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 关注
     */
    private void follow(String code) {

        Call<CheckCodeBean> call = RetrofitService.createMyAPI().Follow(code);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            showBottom(ScanCodeAc.this,response.body().getInfo());
                            finish();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(ScanCodeAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(ScanCodeAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            showBottom(ScanCodeAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(ScanCodeAc.this, response.body().getInfo());
                        }
                    }
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });
    }


}
