package com.fnhelper.photo.diyviews;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnhelper.photo.R;
import com.lcodecore.tkrefreshlayout.IBottomView;


public class LoadMoreView extends FrameLayout implements IBottomView {

    private ImageView refreshArrow;
    private ImageView loadingView;
    private TextView refreshTextView;

    private boolean nomore = false;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View rootView = View.inflate(getContext(), R.layout.view_sinaheader, null);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorWindowBackground));
        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        refreshTextView.setText(pullUpStr);
        refreshArrow.setVisibility(GONE);
        addView(rootView);
    }




    public void setLoadingView(ImageView loadingView) {
        this.loadingView = loadingView;
    }
    public void setArrowResource(@DrawableRes int resId) {
        refreshArrow.setImageResource(resId);
    }

    public void setTextColor(@ColorInt int color) {
        refreshTextView.setTextColor(color);
    }

    public void setPullUpStr(String pullUpStr) {
        this.pullUpStr = pullUpStr;
    }

    public void setReleaseLoadStr(String releaseLoadStr) {
        this.releaseLoadStr = releaseLoadStr;
    }

    public void setNoMore(boolean visible) {
        this.nomore = visible;
    }

    private String pullUpStr = "上拉加载";
    private String releaseLoadStr = "加载中";
    private String noMore = "没有更多";

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        if (nomore) {
            refreshTextView.setText(noMore);
            loadingView.setVisibility(GONE);
        }
        else {
            refreshTextView.setText(releaseLoadStr);
            loadingView.setVisibility(VISIBLE);
        }

        ((AnimationDrawable) loadingView.getDrawable()).start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
