package com.fnhelper.photo.base.recyclerviewadapter;

public interface MultiItemTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}
