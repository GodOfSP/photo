package com.fnhelper.photo.beans;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.fnhelper.photo.AddNewPhotoWordActivity;
import com.fnhelper.photo.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import static com.fnhelper.photo.AddNewPhotoWordActivity.MARK_TYPE_GOOD_NUM;

/**
 * Created by little fly on 2018-10-5.
 */

public class MarkNormalDelegate implements ItemViewDelegate<MarkListItemBean> {

    private onNormalItemThings onNormalItemThings;

    public MarkNormalDelegate(onNormalItemThings onNormalItemThings) {
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
    public void convert(final ViewHolder holder, final MarkListItemBean markListItemBean, final int position) {


        holder.setText(R.id.title, markListItemBean.getTvTitle());
        holder.setText(R.id.content, markListItemBean.getTvContent());
        holder.setText(R.id.open_state, markListItemBean.isOpen() ? "公开" : "仅自己可见");
        ((Switch) holder.getView(R.id.open_state)).setChecked(markListItemBean.isOpen());
        holder.setVisible(R.id.open_state, markListItemBean.getType() != MARK_TYPE_GOOD_NUM);

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
                onNormalItemThings.del(position, markListItemBean.getType());
            }
        });

        ((Switch) holder.getView(R.id.open_state)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (((Switch) holder.getView(R.id.open_state)).isFocused()){
                    if (isChecked) {
                        buttonView.setText("公开");
                        markListItemBean.setOpen(true);
                    } else {
                        buttonView.setText("仅自己可见");
                        markListItemBean.setOpen(false);
                    }
                }

            }
        });
        ((EditText) holder.getView(R.id.content)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( ((EditText) holder.getView(R.id.content)).isFocused()){
                    markListItemBean.setTvContent(s.toString().trim());
                }
            }
        });

    }

    public interface onNormalItemThings {
        void del(int position, int type);
    }

}
