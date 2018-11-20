package com.fnhelper.photo.utils;

import android.app.Activity;
import android.content.Intent;

import com.fnhelper.photo.LoginActivity;
import com.fnhelper.photo.interfaces.Constants;

/**
 * token 过期工具
 */
public class STokenUtil {
    public static void check(Activity activity){
        if (!Constants.isCheckTokenNow){
            Constants.isCheckTokenNow = true;
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
            Constants.isCheckTokenNow = false;
        }

    }
}
