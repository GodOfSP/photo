package com.fnhelper.photo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.EmergeNoticeBean;
import com.fnhelper.photo.index.HomeFragment;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.MineFragment;
import com.fnhelper.photo.mine.ScanCodeAc;
import com.fnhelper.photo.myfans.MyFansFrafment;
import com.fnhelper.photo.myinterst.MyInterstFrafment;
import com.fnhelper.photo.utils.DialogUtils;
import com.fnhelper.photo.utils.STokenUtil;
import com.fnhelper.photo.utils.UpdateAppUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_com_back)
    ImageView tvComBack;
    @BindView(R.id.com_title)
    TextView comTitle;
    @BindView(R.id.com_right)
    ImageView comRight;
    @BindView(R.id.com_code)
    ImageView comCode;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    private ArrayList<Fragment> fragments;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
        setShowExitTips(true);
        initViewPager();
        initBottomBar();
    }

    @Override
    protected void initData() {
        //   检查有无紧急通知
        getEmergeNotice();
        // 检查更新
        UpdateAppUtils.checkVersion(MainActivity.this);
    }

    @Override
    protected void initListener() {
        comRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddNewPhotoWordActivity.class));
            }
        });

        comCode.setVisibility(View.VISIBLE);
        comCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ScanCodeAc.class));
            }
        });
    }


    /**
     * 检查有无紧急通知
     */
    private void getEmergeNotice(){


        Call<EmergeNoticeBean> call = RetrofitService.createMyAPI().GetEmergeNotice();
        call.enqueue(new Callback<EmergeNoticeBean>() {
            @Override
            public void onResponse(Call<EmergeNoticeBean> call, Response<EmergeNoticeBean> response) {
                if (response!=null){
                    if (response.body()!=null){
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData()!=null){
                                if (!TextUtils.isEmpty(response.body().getData().getSTitle())){
                                    DialogUtils.showAlertDialog(MainActivity.this,response.body().getData().getSTitle(),response.body().getData().getSContent(),null,null);
                                }
                            }
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(MainActivity.this);
                            showBottom(MainActivity.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(MainActivity.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<EmergeNoticeBean> call, Throwable t) {

            }
        });

    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("", ""));
        fragments.add(MyInterstFrafment.newInstance("", ""));
        fragments.add(MyFansFrafment.newInstance("", ""));
        fragments.add(MineFragment.newInstance("", ""));
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public Fragment startFragment(int page) {
        if (page >= fragments.size()) {
            return null;
        }
        bottomBar.selectTabAtPosition(page);
        return fragments.get(page);
    }

    /**
     * 初始化BottomBar
     */
    private void initBottomBar() {

        tvComBack.setVisibility(View.GONE);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.home_page:
                        viewPager.setCurrentItem(0, false);
                        comTitle.setText("相册动态");
                        break;
                    case R.id.shopping_cart:
                        viewPager.setCurrentItem(1, false);
                        comTitle.setText("我的关注");
                        break;
                    case R.id.msg:
                        viewPager.setCurrentItem(2, false);
                        comTitle.setText("我的粉丝");
                        break;
                    case R.id.mine:
                        viewPager.setCurrentItem(3, false);
                        comTitle.setText("我的");
                        break;

                    default:
                        break;
                }
            }
        });
    }

}
