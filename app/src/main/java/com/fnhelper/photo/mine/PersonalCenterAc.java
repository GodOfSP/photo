package com.fnhelper.photo.mine;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.diyviews.CustomViewPager;
import com.fnhelper.photo.myfans.MyFansFrafment;
import com.fnhelper.photo.myinterst.MyInterstFrafment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.store_collection)
    ImageView storeCollection;
    @BindView(R.id.head_ConstraintLayout)
    ConstraintLayout headConstraintLayout;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;


    private String mConcernId = "";
    private ArrayList<Fragment> fragmentList = null;
    private ArrayList<String> list_Title = null;

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
        getData();
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

            }
        });


    }


    private void getData() {

        comTitle.setText(mConcernId);
    }

    private void initViewPagerAndTabLayout() {
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        fragmentList.add(new MyInterstFrafment());
        fragmentList.add(new MyFansFrafment());
        fragmentList.add(new MineFragment());
        list_Title.add("one");
        list_Title.add("two");
        list_Title.add("3");
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), PersonalCenterAc.this, fragmentList, list_Title));
        tablayout.setupWithViewPager(viewPager);//此方法就是让tablayout和ViewPager联动
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
}
