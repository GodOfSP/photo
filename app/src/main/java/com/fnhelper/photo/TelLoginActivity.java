package com.fnhelper.photo;

import android.content.Intent;
import android.graphics.Paint;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.diyviews.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        forgetPassword.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
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
                startActivity(new Intent(TelLoginActivity.this,MainActivity.class));
            }
        });

    }

    /**
     * 查看密码
     * @param v
     */
    public  void Hidden(EditText v) {
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.close_eyes_pic));
        }else {
            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.open_eyes_pic));
        }

    }

}
