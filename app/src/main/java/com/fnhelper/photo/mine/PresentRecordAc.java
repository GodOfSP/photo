package com.fnhelper.photo.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.beans.FansListBean;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

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
 * 提现记录
 */
public class PresentRecordAc extends BaseActivity {

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
    @BindView(R.id.title_ll)
    LinearLayout titleLl;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;


    private QuickAdapter<CheckCodeBean> adapter = null;
    private boolean canLoadMore = false;
    private int pageNum = 1;
    private int pageSize = 20;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_present_record);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comTitle.setText("提现记录");
        comRight.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {

        initRecyclerView();
        initTklRefreshLayout();


    }

    @Override
    protected void initListener() {
        tvComBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        refresh.startRefresh();
    }

    private void initRecyclerView() {


        recycler.setLayoutManager(new LinearLayoutManager(PresentRecordAc.this, LinearLayoutManager.VERTICAL, false));


        adapter = new QuickAdapter<CheckCodeBean>(PresentRecordAc.this, R.layout.item_present_record) {
            @Override
            protected void convert(BaseAdapterHelper helper, final CheckCodeBean item, int position) {


            }
        };

        recycler.setAdapter(adapter);

        adapter.add(new CheckCodeBean());
        adapter.add(new CheckCodeBean());
        adapter.add(new CheckCodeBean());
        adapter.add(new CheckCodeBean());

    }
    

    private void initTklRefreshLayout() {

        new TwinklingRefreshLayoutUtil().getUpdateAndLoadMoreTwinkling(PresentRecordAc.this, refresh);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {


            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                pageNum = 1;
                getListData(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (canLoadMore) {
                    pageNum++;
                    getListData(true);
                } else {
                    refreshLayout.finishLoadmore();
                    showBottom(PresentRecordAc.this, "没有更多了 ~");
                }
            }
        });
    }
    

    private void getListData(boolean isLoadMore) {
        Call<CheckCodeBean> call = RetrofitService.createMyAPI().GetPageList(pageSize, pageNum);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData() != null) {

                            }

                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(PresentRecordAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(PresentRecordAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            showBottom(PresentRecordAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(PresentRecordAc.this, response.body().getInfo());
                        }
                    }
                }
                refresh.finishRefreshing();
                refresh.finishLoadmore();

            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                refresh.finishRefreshing();
                refresh.finishLoadmore();
                showBottom(PresentRecordAc.this, "网络异常！");
            }
        });
    }

}