package com.fnhelper.photo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {


    private boolean needLogin = true;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (needLogin){
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.sendEmptyMessageDelayed(1,2000);
    }
}
