package com.fnhelper.photo.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutAppAc extends BaseActivity {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.tag_1)
    TextView tag1;
    @BindView(R.id.content1)
    TextView content1;
    @BindView(R.id.tag_2)
    TextView tag2;
    @BindView(R.id.content2)
    TextView content2;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about_app);
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
        comTitle.setText("关于蜂鸟相册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
