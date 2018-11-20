package com.fnhelper.photo.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.beans.PersonalHeadBean;
import com.fnhelper.photo.diyviews.CustomViewPager;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.myfans.SetFansPermissionsAc;
import com.fnhelper.photo.myfans.SetRemarkNameAc;
import com.fnhelper.photo.utils.STokenUtil;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.List;

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
 * 个人主页
 *
 * @author ls
 */
public class PersonalCenterAc extends BaseActivity {


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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.head_ConstraintLayout)
    ConstraintLayout headConstraintLayout;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;


    private String mConcernId = "";
    private String nickName = "";
    private ArrayList<Fragment> fragmentList = null;
    private ArrayList<String> list_Title = null;

    private int where = 0;

    private EasyPopup mCirclePop;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

        comRight.setImageResource(R.drawable.more_btn);

    }

    @Override
    protected void initData() {

        mConcernId = getIntent().getStringExtra("concernId");
        nickName = getIntent().getStringExtra("nickName");
        where = getIntent().getIntExtra("where",0);
        getData();
        initPop();
        initViewPagerAndTabLayout();

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
                showAuthSettingPop();
            }
        });


    }


    private void getData() {
        getHeadInfo(mConcernId);
    }

    private void initViewPagerAndTabLayout() {
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<Fragment> fragmentList;
        private List<String> list_Title;

        public MyPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, List<String> list_Title) {
            super(fm);
            this.context = context;
            this.fragmentList = fragmentList;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        /**
         * //此方法用来显示tab上的名字
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return list_Title.get(position);
        }
    }

    /**
     * 初始化弹框
     */
    private void initPop() {
        mCirclePop = EasyPopup.create()
                .setContentView(PersonalCenterAc.this, R.layout.auth_pop)
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
                .setDimView((ViewGroup) findViewById(android.R.id.content))
                .apply();

        //设置备注名
        mCirclePop.findViewById(R.id.set_markName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterAc.this, SetRemarkNameAc.class);
                intent.putExtra("id", mConcernId);
                startActivity(intent);
                mCirclePop.dismiss();
            }
        });

        //设置权限
        mCirclePop.findViewById(R.id.set_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterAc.this, SetFansPermissionsAc.class);
                intent.putExtra("id", mConcernId);
                startActivity(intent);
                mCirclePop.dismiss();
            }
        });
        //取消关注
        if (where == 100){  //从我的关注进来的
            mCirclePop.findViewById(R.id.cancel_gz).setVisibility(View.VISIBLE);
        }
        mCirclePop.findViewById(R.id.cancel_gz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelFollow();
                mCirclePop.dismiss();
            }
        });   
        
        //取消
        mCirclePop.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
            }
        });
    }


    /**
     * 取消关注
     */
    private void cancelFollow() {
        Call<CheckCodeBean> call = RetrofitService.createMyAPI().CancelFollow(mConcernId);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            showBottom(PersonalCenterAc.this,response.body().getInfo());
                            finish();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(PersonalCenterAc.this);
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {
            }
        });
    }

    /**
     * 权限设置
     */
    private void showAuthSettingPop() {

        mCirclePop.showAtAnchorView(findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);

    }

    /**
     * 获取头部信息
     */
    private void getHeadInfo(String sConcernId) {

        retrofit2.Call<PersonalHeadBean> call = RetrofitService.createMyAPI().GetConcernUserInfo(sConcernId);
        call.enqueue(new Callback<PersonalHeadBean>() {
            @Override
            public void onResponse(Call<PersonalHeadBean> call, Response<PersonalHeadBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            //头像
                            headPic.setImageURI(response.body().getData().getSHeadImg());
                            //微信号
                            name.setText(response.body().getData().getSWeiXinNo());
                            //title
                            comTitle.setText(nickName);
                            //电话
                            phone.setText(response.body().getData().getSLinkPhone());
                            //介绍
                            content.setText(response.body().getData().getSIntroduce());
                            list_Title.add("动态");
                            list_Title.add("图文(" + response.body().getData().getImageCount() + ")");
                            list_Title.add("视频(" + response.body().getData().getVideoCount() + ")");
                            fragmentList.add(PersonalFreagmentAll.newInstance(mConcernId, "-1"));
                            fragmentList.add(PersonalFreagmentAll.newInstance(mConcernId, "0"));
                            fragmentList.add(PersonalFreagmentAll.newInstance(mConcernId, "1"));
                            viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), PersonalCenterAc.this, fragmentList, list_Title));
                            tablayout.setupWithViewPager(viewPager);//此方法就是让tablayout和ViewPager联动
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(PersonalCenterAc.this);
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(PersonalCenterAc.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<PersonalHeadBean> call, Throwable t) {
                showBottom(PersonalCenterAc.this, "网络异常！");
            }
        });

    }
}
