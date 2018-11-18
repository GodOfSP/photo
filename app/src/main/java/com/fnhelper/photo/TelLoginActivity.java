package com.fnhelper.photo;

import android.content.Intent;
import android.graphics.Paint;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.PhoneLoginBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.BindInputTelActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

public class TelLoginActivity extends BaseActivity {

    @BindView(R.id.phone)
    ClearEditText phone;
    @BindView(R.id.password)
    ClearEditText password;
    @BindView(R.id.show_password)
    ImageView showPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.forget_pass)
    TextView forgetPassword;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tel_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
        forgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hidden(password);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(phone.getText().toString())) {
                    showCenter(TelLoginActivity.this, "手机号为空");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    showCenter(TelLoginActivity.this, "密码为空");
                }else {
                    doLogin();
                }

            }
        });


        //忘记密码
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去绑定
                Intent i = new Intent(TelLoginActivity.this,BindInputTelActivity.class);
                i.putExtra("which",2);
                startActivity(i);
            }
        });

    }

    private void doLogin(){

        loginBtn.setEnabled(false);
        loginBtn.setText("登录中...");

        Call<PhoneLoginBean> call = RetrofitService.createMyAPI().LoginByPhone(phone.getText().toString().trim(),password.getText().toString().trim());
        call.enqueue(new Callback<PhoneLoginBean>() {
            @Override
            public void onResponse(Call<PhoneLoginBean> call, Response<PhoneLoginBean> response) {
                if (response!=null){
                    if (response.body()!=null){
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            Constants.ID =response.body().getData().getID();
                            Constants.sToken =response.body().getData().getsToken();
                            Constants.sHeadImg = response.body().getData().getsHeadImg();
                            Constants.sPhone = response.body().getData().getsPhone();
                            Constants.sLinkPhone = response.body().getData().getsLinkPhone();
                            Constants.sTsNickNameoken = response.body().getData().getsNickName();
                            Constants.wx_num = response.body().getData().getsWeiXinNo();
                            Constants.album_introduce = response.body().getData().getsIntroduce();
                            Constants.album_name = response.body().getData().getsPhotoName();
                            openActivityAndCloseThis(MainActivity.class);
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(TelLoginActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(TelLoginActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            showBottom(TelLoginActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(TelLoginActivity.this, response.body().getInfo());
                        }
                        loginBtn.setEnabled(true);
                        loginBtn.setText("登录");
                    }
                }

            }

            @Override
            public void onFailure(Call<PhoneLoginBean> call, Throwable t) {
                loginBtn.setEnabled(true);
                loginBtn.setText("登录");
            }
        });
    }


    /**
     * 查看密码
     *
     * @param v
     */
    public void Hidden(EditText v) {
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.close_eyes_pic));
        } else {
            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.open_eyes_pic));
        }

    }

}
