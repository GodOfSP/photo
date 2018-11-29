package com.fnhelper.photo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.diyviews.ProgressWebView;

import java.util.List;
import java.util.Locale;

/**
 *   html 专用网页
 */
public class WebViewAc extends BaseActivity {

    public static final int aboutHtml = 1;  // 关于
    public static final int clientPowerHtml = 2;  // 会员权益
    public static final int drawInstructionHtml = 3; // 提现和返佣
    public static final int linkHtml = 4;  // 问题与反馈
    public static final int userAgreementHtml = 5; // 用户协议


    @Override
    public void setContentView() {

        int where = getIntent().getIntExtra("where", 1);

        ProgressWebView mWebView = new ProgressWebView(this);
        String url = "";
        switch (where) {
            case aboutHtml:
                url = sp.getString("aboutHtml", "");
                break;
            case clientPowerHtml:
                url = sp.getString("clientPowerHtml", "");
                break;
            case drawInstructionHtml:
                url = sp.getString("drawInstructionHtml", "");
                break;
            case linkHtml:
                url = sp.getString("linkHtml", "");
                break;
            case userAgreementHtml:
                url = sp.getString("userAgreementHtml", "");
                break;
        }
        if (!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }
        setContentView(mWebView);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


}
