package com.fnhelper.photo.myfans;

import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.interfaces.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

/**
 * 设置一些列的东西
 */
public class SetRemarkNameAc extends BaseActivity {

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
    @BindView(R.id.div_top)
    View divTop;
    @BindView(R.id.set_title)
    TextView setTitle;
    @BindView(R.id.set_content)
    EditText setContent;
    @BindView(R.id.div_bottom)
    View divBottom;
    @BindView(R.id.set_cl)
    ConstraintLayout setCl;


    private String id = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_set_remark_name);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
        comTitle.setText("设置备注名");
        comRight.setImageResource(R.drawable.completed_btn);
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void initListener() {


        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRemarkName();
            }
        });

    }


    /**
     * 设置备注名
     */
    private void setRemarkName() {
        if (!TextUtils.isEmpty(setContent.getText().toString())) {

            loadingDialog.setHintText("保存中");
            loadingDialog.show();
            retrofit2.Call<CheckCodeBean> call = RetrofitService.createMyAPI().SetFansRemark(id, setContent.getText().toString().trim());
            call.enqueue(new Callback<CheckCodeBean>() {
                @Override
                public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                    if (response != null) {
                        if (response.body() != null) {
                            if (response.body().getCode() == CODE_SUCCESS) {
                                //成功
                                showBottom(SetRemarkNameAc.this, response.body().getInfo());
                                finish();
                            } else if (response.body().getCode() == CODE_ERROR) {
                                //失败
                                showBottom(SetRemarkNameAc.this, response.body().getInfo());
                            } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                                //服务错误
                                showBottom(SetRemarkNameAc.this, response.body().getInfo());
                            } else if (response.body().getCode() == CODE_TOKEN) {
                                //登录过期
                                showBottom(SetRemarkNameAc.this, response.body().getInfo());
                            } else if (response.body().getCode() == CODE_TOKEN) {
                                //账号冻结
                                showBottom(SetRemarkNameAc.this, response.body().getInfo());
                            }
                        }
                    }

                    loadingDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                    showBottom(SetRemarkNameAc.this, "网络异常！");
                    loadingDialog.dismiss();
                }
            });
        } else {
            showBottom(SetRemarkNameAc.this, "输入为空!");
        }
    }
}
