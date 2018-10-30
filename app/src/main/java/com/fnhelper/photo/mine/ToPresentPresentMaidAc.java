package com.fnhelper.photo.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToPresentPresentMaidAc extends BaseActivity {


    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.com_code)
    ImageView comCode;
    @BindView(R.id.txje_title)
    TextView txjeTitle;
    @BindView(R.id.txjl_title)
    TextView txjlTitle;
    @BindView(R.id.fyjl_title)
    TextView fyjlTitle;
    @BindView(R.id.money_title)
    TextView moneyTitle;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.dqktx_title)
    TextView dqktxTitle;
    @BindView(R.id.ktx_money)
    TextView ktxMoney;
    @BindView(R.id.qbtx_btn)
    Button qbtxBtn;
    @BindView(R.id.rhhdtx_title)
    TextView rhhdtxTitle;
    @BindView(R.id.tx_btn)
    Button txBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_to_present_present_maid);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("提现与返佣");
        comRight.setImageResource(R.drawable.more_btn);

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

        //提现记录
        txjlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToPresentPresentMaidAc.this, PresentRecordAc.class);
                startActivity(intent);
            }
        });

        //返佣记录
        fyjlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToPresentPresentMaidAc.this, MaidRecordAc.class);
                startActivity(intent);
            }
        });
    }

}
