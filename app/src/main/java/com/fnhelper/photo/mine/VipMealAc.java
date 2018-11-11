package com.fnhelper.photo.mine;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.VipMealListBean;
import com.fnhelper.photo.interfaces.Constants;
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

public class VipMealAc extends BaseActivity {

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
    @BindView(R.id.head_pic)
    SimpleDraweeView headPic;
    @BindView(R.id.vip_type)
    ImageView vipType;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.expiry_date_title)
    TextView expiryDateTitle;
    @BindView(R.id.expiry_date)
    TextView expiryDate;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.vip_tv)
    TextView vipTv;
    @BindView(R.id.presentAndMaid_tv)
    TextView presentAndMaidTv;


    private QuickAdapter<VipMealListBean.DataBean> adapter = null;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vip_meal);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
        comTitle.setText("会员套餐信息");
        comRight.setVisibility(View.GONE);
        headPic.setImageURI(Constants.sHeadImg);
        userName.setText(Constants.sTsNickNameoken);
        if (Constants.isVIP) {

            expiryDate.setText(Constants.vip_exi_time);
        } else {
            expiryDate.setText("暂不是会员");
            expiryDateTitle.setVisibility(View.GONE);
            vipType.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRv();
        getVipMealList();
    }

    @Override
    protected void initListener() {

    }


    private void initRv() {
        recycler.setLayoutManager(new LinearLayoutManager(VipMealAc.this, LinearLayoutManager.VERTICAL, false));
        adapter = new QuickAdapter<VipMealListBean.DataBean>(VipMealAc.this, R.layout.item_vipmeal_list) {
            @Override
            protected void convert(BaseAdapterHelper helper, VipMealListBean.DataBean item, int position) {

                helper.setText(R.id.yj_num,item.getDOldPrices());
                helper.setText(R.id.title_num,item.getDVipPrices());
                helper.setText(R.id.title,item.getSVipName()+":  ¥");
                helper.getTextView(R.id.yj_num).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

               // setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

            }
        };
        recycler.setAdapter(adapter);
    }

    private void getVipMealList() {
        retrofit2.Call<VipMealListBean> call = RetrofitService.createMyAPI().GetVipPackageList();
        call.enqueue(new Callback<VipMealListBean>() {
            @Override
            public void onResponse(Call<VipMealListBean> call, Response<VipMealListBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            adapter.replaceAll(response.body().getData());

                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<VipMealListBean> call, Throwable t) {
                showBottom(VipMealAc.this, "网络异常！");
            }
        });
    }
}
