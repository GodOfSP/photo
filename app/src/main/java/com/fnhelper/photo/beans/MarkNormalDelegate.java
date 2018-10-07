package com.fnhelper.photo.beans;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

public class MarkNormalDelegate implements ItemViewDelegate<MarkListItemBean> {

    private onNormalItemThings onNormalItemThings;

    public MarkNormalDelegate( onNormalItemThings onNormalItemThings) {
        this.onNormalItemThings = onNormalItemThings;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_marklist_normal;
    }

    @Override
    public boolean isForViewType(MarkListItemBean item, int position) {
        if (item.getType() != AddNewPhotoWordActivity.MARK_TYPE_NONE && item.getType() != AddNewPhotoWordActivity.MARK_TYPE_ALL && item.getType() != AddNewPhotoWordActivity.MARK_TYPE_TV) {
            return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder holder, final MarkListItemBean markListItemBean, final int position) {


        holder.setText(R.id.title,markListItemBean.getTvTitle());
        holder.setText(R.id.content,markListItemBean.getTvContent());
        holder.setText(R.id.open_state,markListItemBean.isOpen()?"公开":"仅自己可见");

        SwipeLayout swipeLayout = holder.getView(R.id.swipe);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                super.onUpdate(layout, leftOffset, topOffset);
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                super.onStartOpen(layout);

            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                super.onStartClose(layout);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                super.onHandRelease(layout, xvel, yvel);
            }

            @Override
            public void onOpen(SwipeLayout layout) {//划出时的监听
            }
        });
        holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNormalItemThings.del(position,markListItemBean.getType());
            }
        });
        holder.setOnClickListener(R.id.modify, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNormalItemThings.modify(markListItemBean.getType());
            }
        });
    }

    public interface onNormalItemThings {
        void del(int position,int type);

        void modify(int type);
    }

}
