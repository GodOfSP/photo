package com.fnhelper.photo.utils;

import android.app.Activity;

import com.fnhelper.photo.diyviews.LoadMoreView;
import com.fnhelper.photo.diyviews.MyRefreshView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;


public class TwinklingRefreshLayoutUtil {
    public TwinklingRefreshLayoutUtil() {
    }

    public TwinklingRefreshLayout getUpdateAndLoadMoreTwinkling(Activity activity, TwinklingRefreshLayout refreshLayoutBuyRecord) {
        final MyRefreshView sinaRefreshView = new MyRefreshView(activity);
        refreshLayoutBuyRecord.setHeaderView(sinaRefreshView);
        final LoadMoreView loadingView = new LoadMoreView(activity);
        refreshLayoutBuyRecord.setBottomView(loadingView);
        return refreshLayoutBuyRecord;
    }

    public TwinklingRefreshLayout getLoadMoreTwinkling(Activity activity, TwinklingRefreshLayout refreshLayoutBuyRecord) {
        final LoadMoreView loadingView = new LoadMoreView(activity);
        refreshLayoutBuyRecord.setBottomView(loadingView);
        refreshLayoutBuyRecord.setEnableRefresh(false); //不刷新
        refreshLayoutBuyRecord.setEnableOverScroll(true);
        return refreshLayoutBuyRecord;
    }

    public TwinklingRefreshLayout getUpdateTwinkling(Activity activity, TwinklingRefreshLayout refreshLayoutBuyRecord) {
        final MyRefreshView sinaRefreshView = new MyRefreshView(activity);
        refreshLayoutBuyRecord.setHeaderView(sinaRefreshView);
        refreshLayoutBuyRecord.setEnableLoadmore(false); //不加载更多
        refreshLayoutBuyRecord.setEnableOverScroll(true);
        return refreshLayoutBuyRecord;
    }

    //纯净模式
    public TwinklingRefreshLayout getPureTwinkling(Activity activity, TwinklingRefreshLayout refreshLayoutBuyRecord) {
        refreshLayoutBuyRecord.setPureScrollModeOn();
        return refreshLayoutBuyRecord;
    }

}
