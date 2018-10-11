package com.fnhelper.photo.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.fnhelper.photo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 验证码计时
 */public class TimeUtils {



    private int time=60;

    private Timer timer;

    private Button btnSure;

    private String btnText;

    public TimeUtils(Button btnSure, String btnText) {
        super();
        this.btnSure = btnSure;
        this.btnText = btnText;
    }





    public void cancel(){
        time = -100;
        handler.sendEmptyMessage(1);
    }

    public void runTimer(){
        timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run(){
                time--;
                Message msg=handler.obtainMessage();
                msg.what=1;
                handler.sendMessage(msg);

            }
        };


        timer.schedule(task, 100, 1000);
    }


    private Handler handler =new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:

                    if(time>0){
                        btnSure.setEnabled(false);
                        btnSure.setText(time+"秒");
                        btnSure.setTextSize(14);
                        btnSure.setBackgroundResource(R.drawable.bg_full_gray_r4);
                    }else{

                        timer.cancel();
                        btnSure.setText(btnText);
                        btnSure.setEnabled(true);
                        btnSure.setTextSize(14);
                        btnSure.setBackgroundResource(R.drawable.selector_full_green_r4);
                    }

                    break;


                default:
                    break;
            }

        };
    };



}
