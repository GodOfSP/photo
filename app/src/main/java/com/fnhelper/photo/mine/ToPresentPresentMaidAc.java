package com.fnhelper.photo.mine;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.BalanceBean;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

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
    ClearEditText moneyEt;
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
    @BindView(R.id.viewGroup)
    ConstraintLayout viewGroup;


    private EasyPopup mTxPop;
    private String moneyNow = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_to_present_present_maid);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("提现与返佣");
        comRight.setImageResource(R.drawable.more_btn);
        initPop();


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

        /**
         * 提现按钮
         */
        txBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyNow = moneyEt.getText().toString().trim();
                if (TextUtils.isEmpty(moneyNow)) {
                    showCenter(ToPresentPresentMaidAc.this, "请输入提现金额！");
                } else if (Double.parseDouble(moneyNow) <= 0) {
                    showCenter(ToPresentPresentMaidAc.this, "提现金额输入有误！");
                } else {
                    tx();
                }
            }
        });

        /**
         * 全部提现
         */
        qbtxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyEt.setText(ktxMoney.getText().toString());
                tx();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBalance();
    }

    /**
     * 获取可提现余额
     */
    private void getBalance() {

        Call<BalanceBean> call = RetrofitService.createMyAPI().GetBalance();
        call.enqueue(new Callback<BalanceBean>() {
            @Override
            public void onResponse(Call<BalanceBean> call, Response<BalanceBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData() != null) {
                                ktxMoney.setText(response.body().getData().getDBalance());
                            }
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        }
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BalanceBean> call, Throwable t) {
                showBottom(ToPresentPresentMaidAc.this, "网络出错！");
            }
        });
    }

    /**
     * 初始化弹框
     */
    private void initPop() {
        mTxPop = EasyPopup.create()
                .setContentView(ToPresentPresentMaidAc.this, R.layout.tx_pop)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView(viewGroup)
                .apply();

        ((SimpleDraweeView) mTxPop.findViewById(R.id.head_pic)).setImageURI(Constants.sHeadImg);
        ((TextView) mTxPop.findViewById(R.id.user_name)).setText(Constants.sTsNickNameoken);

        mTxPop.findViewById(R.id.tx_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxPop.dismiss();
                txHttp();
            }
        });
    }

    /**
     * 提现操作
     */
    private void tx() {
        ((TextView) mTxPop.findViewById(R.id.je)).setText(moneyNow);
        mTxPop.showAtAnchorView(ToPresentPresentMaidAc.this.findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);
    }

    /**
     * 提现
     */
    private void txHttp() {

        loadingDialog.setHintText("处理中");
        loadingDialog.show();

        Call<CheckCodeBean> call = RetrofitService.createMyAPI().Draw(moneyNow);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(ToPresentPresentMaidAc.this, response.body().getInfo());
                        }
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                showBottom(ToPresentPresentMaidAc.this, "网络出错！");
            }
        });
    }

}
