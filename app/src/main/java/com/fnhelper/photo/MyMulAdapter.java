package com.fnhelper.photo;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by little fly on 2018-11-24.
 */

public class MyMulAdapter extends MultiItemTypeAdapter {


    public MyMulAdapter(Context context, List datas) {
        super(context, datas);
    }

    public void remove(int index) {
        mDatas.remove(index);
        notifyDataSetChanged();
    }
}
