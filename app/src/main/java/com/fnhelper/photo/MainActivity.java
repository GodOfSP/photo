package com.fnhelper.photo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.index.HomeFragment;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.mine.MineFragment;
import com.fnhelper.photo.mine.ScanCodeAc;
import com.fnhelper.photo.myfans.MyFansFrafment;
import com.fnhelper.photo.myinterst.MyInterstFrafment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private void share() {
        //初始化微信

        IWXAPI wxAPI = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APPID, true);
        wxAPI.registerApp(Constants.WECHAT_APPID);

        WXTextObject wxTextObject = new WXTextObject();
        wxTextObject.text = "test";
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.description = "heheh";
        wxMediaMessage.mediaObject = wxTextObject;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = new GetMessageFromWX.Req().transaction;
        req.message = wxMediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        //  req.scene =  SendMessageToWX.Req.WXSceneSession;
        wxAPI.sendReq(req);
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
