package com.fnhelper.photo.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.LoginActivity;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 系统设置
 */
public class SystemSettingAc extends BaseActivity {

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
    @BindView(R.id.bd_tel)
    RelativeLayout bdTel;
    @BindView(R.id.system_setting)
    RelativeLayout systemSetting;
    @BindView(R.id.logout)
    TextView logout;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("设置");
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        comRight.setVisibility(View.GONE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 退出
     */
    private void logOut() {


        DialogUtils.showLogoutDialog(SystemSettingAc.this, new DialogUtils.OnCommitListener() {
            @Override
            public void onCommit() {
                Constants.ID = "";
                Constants.sToken = "";
                Constants.sTsNickNameoken = "";
                Constants.sPhone = "";
                Constants.sLinkPhone = "";
                Constants.sHeadImg = "";
                Constants.wx_num = "";
                Constants.album_introduce = "";
                Constants.album_name = "";
                Constants.vip_exi_time = "";
                Constants.isVIP = false;

                editor.putString("ID", "");
                editor.putString("sToken", "");
                editor.commit();

                finishAll();
                openActivity(LoginActivity.class);
            }
        });


    }
}
