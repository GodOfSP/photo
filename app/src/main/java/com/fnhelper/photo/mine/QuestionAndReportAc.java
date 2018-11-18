package com.fnhelper.photo.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 问题与反馈
 */
public class QuestionAndReportAc extends BaseActivity {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.com_code)
    ImageView comCode;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.head_bg)
    ImageView headBg;
    @BindView(R.id.bindPhone)
    RelativeLayout bindPhone;
    @BindView(R.id.tel)
    RelativeLayout tel;
    @BindView(R.id.time)
    RelativeLayout time;
    @BindView(R.id.qq)
    RelativeLayout qq;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_question_and_report);
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
        comTitle.setText("问题与反馈");
        comRight.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
