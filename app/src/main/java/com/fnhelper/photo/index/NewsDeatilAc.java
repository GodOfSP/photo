package com.fnhelper.photo.index;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.NewsListBean;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.STokenUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

/**
 * 动态详情
 */
public class NewsDeatilAc extends BaseActivity {


    private String id = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.item_news);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        getData();
    }

    @Override
    protected void initListener() {

    }

    private void getData(){

        Call<NewsListBean> call = RetrofitService.createMyAPI().GetImageText(id);
        call.enqueue(new Callback<NewsListBean>() {
            @Override
            public void onResponse(Call<NewsListBean> call, Response<NewsListBean> response) {
                if (response!=null){
                    if (response.body()!=null){
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData()!=null){

                            }
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(NewsDeatilAc.this);
                            showBottom(NewsDeatilAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(NewsDeatilAc.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsListBean> call, Throwable t) {

            }
        });
    }
}
