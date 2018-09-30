package com.fnhelper.photo.diyviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fnhelper.photo.R;


public class LoadView extends LinearLayout{

    private View mLoadingPB;
    private TextView mLoadingTV;
    //提示
    private static String NO_MORE = "无更多数据...";
    private static String LOADING = "加载中...";
    private boolean isOver = true;

    public LoadView(Context context) {
        super(context);
        init(context);
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.layout_load_view,this);
        mLoadingPB = findViewById(R.id.pb_loading);
        mLoadingTV = (TextView)findViewById(R.id.tv_loading);
    }

    public void isLoadable(boolean isLoadable){
        this.setVisibility(VISIBLE);
        if (isLoadable){
            mLoadingTV.setText(LOADING);
            mLoadingPB.setVisibility(VISIBLE);
        }else{
            mLoadingTV.setText(NO_MORE);
            mLoadingPB.setVisibility(GONE);
            if (isOver){
                isOver = false;
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadView.this.setVisibility(GONE);
                        isOver = true;
                    }
                },1500);
            }
        }
    }
}
