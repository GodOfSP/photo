package com.fnhelper.photo.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.GetCodeBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.DialogUtils;
import com.fnhelper.photo.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

/**
 * 绑定 -- 输入手机号
 */
public class BindInputTelActivity extends BaseActivity {


    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.phone_et)
    ClearEditText phoneEt;
    @BindView(R.id.code_et)
    ClearEditText codeEt;
    @BindView(R.id.btn_send_code)
    Button btnSendCode;
    @BindView(R.id.next_btn)
    Button nextBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bind_input_tel);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //2 代表忘记密码
        if (getIntent().getIntExtra("which", 2) == 2) {
            comTitle.setText("忘记密码");
            //1 代表绑定手机号
        } else if (getIntent().getIntExtra("which", 2) == 1) {
            comTitle.setText("绑定手机号");
        }
        comRight.setVisibility(View.GONE);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        //发送验证码
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phoneEt.getText().toString().trim()) && DialogUtils.isMobile(phoneEt.getText().toString().trim())) {
                    sendCode();
                } else {
                    showCenter(BindInputTelActivity.this, "请先输入正确的手机号！");
                }
            }
        });

        //验证
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCode();
            }
        });

    }

    private void sendCode() {

        sPhone = phoneEt.getText().toString().trim();

        new TimeUtils(btnSendCode, "发送验证码").runTimer();
        retrofit2.Call<GetCodeBean> call2 = RetrofitService.createMyAPI().GetMobileCode(phoneEt.getText().toString().trim(), getIntent().getIntExtra("which", 1));
        call2.enqueue(new retrofit2.Callback<GetCodeBean>() {

            @Override
            public void onResponse(Call<GetCodeBean> call, Response<GetCodeBean> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == CODE_SUCCESS) {
                        //成功
                        showBottom(BindInputTelActivity.this, "发送成功");
                        getCode = response.body().getData();
                    } else if (response.body().getCode() == CODE_ERROR) {
                        //失败
                        showBottom(BindInputTelActivity.this, response.body().getInfo());
                    } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                        //服务错误
                        showBottom(BindInputTelActivity.this, response.body().getInfo());
                    } else if (response.body().getCode() == CODE_TOKEN) {
                        //登录过期
                        showBottom(BindInputTelActivity.this, response.body().getInfo());
                    } else if (response.body().getCode() == CODE_TOKEN) {
                        //账号冻结
                        showBottom(BindInputTelActivity.this, response.body().getInfo());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCodeBean> call, Throwable t) {

            }
        });
    }

    private String getCode;
    private String sPhone;

    /**
     * 校验验证码
     */
    private void checkCode() {

        String code = codeEt.getText().toString().trim();

        if (!TextUtils.isEmpty(code)) {
            if (code.equals(getCode)) {
                Bundle bundle = new Bundle();
                bundle.putString("sPhone", sPhone);
                bundle.putString("sCode", getCode);
                openActivityAndCloseThis(BindSetNewPassWordActivity.class, bundle);
            } else {
                showCenter(BindInputTelActivity.this, "验证码不对哦");
            }
        } else {
            showCenter(BindInputTelActivity.this, "请输入验证码");
        }
    }

}
