package com.fnhelper.photo.mine;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;

/**
 *   个人主页
 * @author ls
 */
public class PersonalCenterAc extends BaseActivity {




    private String mConcernId = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personal_center);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

        mConcernId =  getIntent().getStringExtra("concernId");

    }

    @Override
    protected void initListener() {

    }
}
