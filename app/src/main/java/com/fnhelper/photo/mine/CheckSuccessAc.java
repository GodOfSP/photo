package com.fnhelper.photo.mine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 绑定手机号/修改密码 成功
 */
public class CheckSuccessAc extends BaseActivity {


    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.back_btn)
    Button backBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_check_success);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("验证成功/完成绑定");
        comRight.setVisibility(View.GONE);
        tv2.setText(getIntent().getStringExtra("tel")+"+密码");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
