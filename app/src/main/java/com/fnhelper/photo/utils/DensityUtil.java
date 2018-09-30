package com.fnhelper.photo.utils;

import android.content.Context;

public class DensityUtil {

    public static float scale;

    static boolean isDone = false;

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        if (!isDone){
            scale = context.getResources().getDisplayMetrics().density;
            isDone = true;
        }
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        if (!isDone){
            scale = context.getResources().getDisplayMetrics().density;
            isDone = true;
        }
        return (int) (pxValue / scale + 0.5f);  
    }  
} 