package com.fnhelper.photo.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.NoticeBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeDetailAc extends BaseActivity {

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
    @BindView(R.id.notice_title)
    TextView noticeTitle;
    @BindView(R.id.notice_content)
    TextView noticeContent;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.sure)
    TextView sure;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("公告详情");
        comRight.setVisibility(View.GONE);
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {

        NoticeBean.DataBean.RowsBean data = getIntent().getParcelableExtra("data");

        noticeTitle.setText(data.getSTitle());
        time.setText(data.getDInsertTime());
        noticeContent.setText(data.getSContent());

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initListener() {

    }
}
