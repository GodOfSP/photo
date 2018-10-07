package com.fnhelper.photo.beans;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.fnhelper.photo.AddNewPhotoWordActivity;
import com.fnhelper.photo.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by little fly on 2018-10-5.
 */

public class MarkNoneDelegate implements ItemViewDelegate<MarkListItemBean> {



    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_marklist_none;
    }

    @Override
    public boolean isForViewType(MarkListItemBean item, int position) {
        if (item.getType() == AddNewPhotoWordActivity.MARK_TYPE_NONE) {

            return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder holder, MarkListItemBean markListItemBean, int position) {



    }
}
