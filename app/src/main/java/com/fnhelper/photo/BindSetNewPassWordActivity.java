package com.fnhelper.photo;

import android.os.Bundle;
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

/**
 * 设置新密码
 */
public class BindSetNewPassWordActivity extends BaseActivity {


    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.new_password)
    ClearEditText newPassword;
    @BindView(R.id.show_password)
    ImageView showPassword;
    @BindView(R.id.new_password_again)
    ClearEditText newPasswordAgain;
    @BindView(R.id.show_password_again)
    ImageView showPasswordAgain;
    @BindView(R.id.login_btn)
    Button loginBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bind_set_new_pass_word);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comRight.setVisibility(View.GONE);
        comTitle.setText("设置新密码");
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hidden(newPassword);
            }
        });
        showPasswordAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hidden1(newPasswordAgain);
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

    /**
     * 查看密码
     *
     * @param v
     */
    public void Hidden1(EditText v) {
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            showPasswordAgain.setImageDrawable(getResources().getDrawable(R.drawable.close_eyes_pic));
        } else {
            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPasswordAgain.setImageDrawable(getResources().getDrawable(R.drawable.open_eyes_pic));
        }

    }

}
